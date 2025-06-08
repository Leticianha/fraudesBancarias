package detectorfraude.controller;

import detectorfraude.dao.DebitoAutomaticoDAO;
import detectorfraude.model.DebitoAutomatico;
import detectorfraude.util.ConexaoMySQL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DebitoController {

    public void cadastrarDebito(DebitoAutomatico debito) {
        try (Connection connection = ConexaoMySQL.getConexao()) {
            DebitoAutomaticoDAO dao = new DebitoAutomaticoDAO(connection);
            dao.inserir(debito);
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar débito: " + e.getMessage());
        }
    }

    public List<DebitoAutomatico> listarDebitos() {
        try (Connection connection = ConexaoMySQL.getConexao()) {
            DebitoAutomaticoDAO dao = new DebitoAutomaticoDAO(connection);
            return dao.listarTodos();
        } catch (SQLException e) {
            System.err.println("Erro ao listar débitos: " + e.getMessage());
            return null;
        }
    }
}
