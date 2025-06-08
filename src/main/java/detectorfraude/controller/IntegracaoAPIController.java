package detectorfraude.controller;

import detectorfraude.dao.EmpresaDAO;
import detectorfraude.model.Empresa;
import detectorfraude.util.ConexaoMySQL;

import java.sql.Connection;
import java.sql.SQLException;

public class IntegracaoAPIController {

    public void testarConsultaCnpj(String cnpj) {
        try (Connection connection = ConexaoMySQL.getConexao()) {
            EmpresaDAO empresaDAO = new EmpresaDAO(connection);

            // Aqui você implementaria o método de consulta real à API
            // Simulando retorno fictício de uma empresa para teste:
            Empresa empresa = new Empresa();
            empresa.setCnpj(cnpj);
            empresa.setRazaoSocial("Empresa Exemplo LTDA");
            empresa.setSituacaoCadastral("Ativa");

            // Você pode salvar no banco se quiser:
            // empresaDAO.inserir(empresa);

            if (empresa != null) {
                System.out.println("Razão Social: " + empresa.getRazaoSocial());
                System.out.println("CNPJ: " + empresa.getCnpj());
                System.out.println("Situação Cadastral: " + empresa.getSituacaoCadastral());
            } else {
                System.out.println("CNPJ não encontrado ou erro na consulta.");
            }
        } catch (SQLException e) {
            System.out.println("Erro na conexão ou consulta: " + e.getMessage());
        }
    }
}