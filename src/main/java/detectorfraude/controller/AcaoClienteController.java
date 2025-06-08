package detectorfraude.controller;

import detectorfraude.dao.AcaoClienteDAO;
import detectorfraude.model.AcaoCliente;
import detectorfraude.util.ConexaoMySQL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AcaoClienteController {

    public void registrarAcao(AcaoCliente acao) {
        try (Connection connection = ConexaoMySQL.getConexao()) {
            AcaoClienteDAO dao = new AcaoClienteDAO(connection);
            dao.inserir(acao);
        } catch (SQLException e) {
            System.err.println("Erro ao registrar ação do cliente: " + e.getMessage());
        }
    }

    public List<AcaoCliente> listarAcoes() {
        try (Connection connection = ConexaoMySQL.getConexao()) {
            AcaoClienteDAO dao = new AcaoClienteDAO(connection);
            return dao.listarTodos();
        } catch (SQLException e) {
            System.err.println("Erro ao listar ações do cliente: " + e.getMessage());
            return null;
        }
    }
}
