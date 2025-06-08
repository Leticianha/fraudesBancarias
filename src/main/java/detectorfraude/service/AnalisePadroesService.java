package detectorfraude.service;

import detectorfraude.model.DebitoAutomatico;
import detectorfraude.dao.EmpresaDAO;
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
        return !getMotivosSuspeita(debito).isEmpty();
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

            if (!empresaDoDebito.isCnpjValido()) {
                motivos.add("🚨 CNPJ inválido detectado.");
            }

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