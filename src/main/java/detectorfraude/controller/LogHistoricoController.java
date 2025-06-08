package detectorfraude.controller;

import detectorfraude.dao.LogHistoricoDAO;
import detectorfraude.model.LogHistorico;
import detectorfraude.util.ConexaoMySQL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class LogHistoricoController {

    public void registrarEvento(LogHistorico log) {
        try (Connection connection = ConexaoMySQL.getConexao()) {
            LogHistoricoDAO dao = new LogHistoricoDAO(connection);
            dao.inserir(log);
        } catch (SQLException e) {
            System.err.println("Erro ao registrar evento no log: " + e.getMessage());
        }
    }

    public List<LogHistorico> listarEventos() {
        try (Connection connection = ConexaoMySQL.getConexao()) {
            LogHistoricoDAO dao = new LogHistoricoDAO(connection);
            return dao.listarTodos();
        } catch (SQLException e) {
            System.err.println("Erro ao listar eventos: " + e.getMessage());
            return null;
        }
    }
}
