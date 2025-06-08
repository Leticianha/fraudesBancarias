package detectorfraude.service;

import detectorfraude.model.DebitoAutomatico;
import detectorfraude.dao.EmpresaDAO;
import detectorfraude.model.Empresa;
import detectorfraude.util.ConexaoMySQL;
import detectorfraude.util.FuzzyMatchingUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AnalisePadroesService {

    private static final double LIMIAR_SIMILARIDADE = 0.85; // limite para nome parecido

    public boolean analisarDebito(DebitoAutomatico debito) {
        boolean suspeito = false;

        try (Connection connection = ConexaoMySQL.getConexao()) {
            EmpresaDAO empresaDAO = new EmpresaDAO(connection);
            List<Empresa> empresas = empresaDAO.listarTodas();

            // 1. Buscar a empresa do débito
            Empresa empresaDoDebito = null;
            for (Empresa e : empresas) {
                if (e.getEmpresaId() == debito.getEmpresaId()) {
                    empresaDoDebito = e;
                    break;
                }
            }

            if (empresaDoDebito == null) {
                System.out.println("Empresa do débito não encontrada.");
                return false;
            }

            // 2. Verifica se o CNPJ é inválido
            if (!empresaDoDebito.isCnpjValido()) {
                System.out.println("🚨 CNPJ inválido detectado.");
                suspeito = true;
            }

            // 3. Verifica similaridade de nome com outras empresas
            for (Empresa outra : empresas) {
                if (outra.getEmpresaId() != empresaDoDebito.getEmpresaId()) {
                    double similaridade = FuzzyMatchingUtil.calcularSimilaridade(empresaDoDebito.getNome(), outra.getNome());
                    if (similaridade >= LIMIAR_SIMILARIDADE) {
                        System.out.println("🚨 Nome semelhante ao de outra empresa detectado: " + outra.getNome());
                        suspeito = true;
                        break;
                    }
                }
            }

            // 4. Verifica valor pequeno e recorrente
            if (debito.getValor().doubleValue() <= 50.00 &&
                (debito.getTipoRecorrencia().equals("Mensal") || debito.getTipoRecorrencia().equals("Semanal"))) {
                System.out.println("🚨 Débito com valor pequeno e recorrente.");
                suspeito = true;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao analisar débito: " + e.getMessage());
        }

        return suspeito;
    }
}
