package detectorfraude.service;

import detectorfraude.dao.DebitoAutomaticoDAO;
import detectorfraude.dao.EmpresaDAO;
import detectorfraude.model.DebitoAutomatico;
import detectorfraude.model.Empresa;
import detectorfraude.util.ConexaoMySQL;
import detectorfraude.util.FuzzyMatchingUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnalisePadroesService {

    private static final double LIMIAR_SIMILARIDADE = 0.85;

    public boolean analisarDebito(DebitoAutomatico debito) {
        List<String> motivos = getMotivosSuspeita(debito);

        if (!motivos.isEmpty()) {
            debito.setStatusSuspeita("Suspeito");

            try (Connection connection = ConexaoMySQL.getConexao()) {
                DebitoAutomaticoDAO dao = new DebitoAutomaticoDAO(connection);
                dao.atualizarStatusSuspeita(debito.getDebitoId(), "Suspeito");
            } catch (SQLException e) {
                System.err.println("Erro ao atualizar status suspeita: " + e.getMessage());
            }

            return true;
        }

        return false;
    }

    public List<String> getMotivosSuspeita(DebitoAutomatico debito) {
        List<String> motivos = new ArrayList<>();

        try (Connection connection = ConexaoMySQL.getConexao()) {
            EmpresaDAO empresaDAO = new EmpresaDAO(connection);
            List<Empresa> empresas = empresaDAO.listarTodas();

            Empresa empresaDoDebito = empresas.stream()
                    .filter(e -> e.getEmpresaId() == debito.getEmpresaId())
                    .findFirst()
                    .orElse(null);

            if (empresaDoDebito == null) {
                motivos.add("Empresa do débito não encontrada.");
                return motivos;
            }

            // 🚨 Nome diferente do oficial na Receita Federal
            CnpjService cnpjService = new CnpjService();
            Empresa empresaReceita = cnpjService.consultarCnpj(empresaDoDebito.getCnpj());

            if (empresaReceita != null) {
                SimilaridadeNomeService similaridade = new SimilaridadeNomeService();
                boolean saoSimilares = similaridade.nomesSaoSimilares(
                        empresaDoDebito.getNome(), empresaReceita.getRazaoSocial());

                if (!saoSimilares) {
                    motivos.add("🚨 Nome da empresa diferente do nome oficial da Receita Federal.");
                }
            }

            // 🚨 CNPJ inválido
            if (!empresaDoDebito.isCnpjValido()) {
                motivos.add("🚨 CNPJ inválido detectado.");
            }

            // 🚨 Nome semelhante a outras empresas do banco
            for (Empresa outra : empresas) {
                if (outra.getEmpresaId() != empresaDoDebito.getEmpresaId()) {
                    double similaridade = FuzzyMatchingUtil.calcularSimilaridade(
                            empresaDoDebito.getNome(), outra.getNome());
                    if (similaridade >= LIMIAR_SIMILARIDADE) {
                        motivos.add("🚨 Nome semelhante ao de outra empresa: " + outra.getNome());
                        break;
                    }
                }
            }

            // 🚨 Débito com valor pequeno e recorrente
            if (debito.getValor().doubleValue() <= 50.00 &&
                    ("Mensal".equals(debito.getTipoRecorrencia()) || "Semanal".equals(debito.getTipoRecorrencia()))) {
                motivos.add("🚨 Débito com valor pequeno e recorrente.");
            }

        } catch (SQLException e) {
            motivos.add("Erro ao analisar débito: " + e.getMessage());
        }

        return motivos;
    }
}
