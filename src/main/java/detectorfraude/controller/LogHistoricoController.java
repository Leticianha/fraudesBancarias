package detectorfraude.controller;

import detectorfraude.dao.LogHistoricoDAO;
import detectorfraude.model.LogHistorico;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class LogHistoricoController {

    private LogHistoricoDAO logDAO;

    public LogHistoricoController(Connection connection) {
        this.logDAO = new LogHistoricoDAO(connection);
    }

    public void inserir(LogHistorico log) throws SQLException {
        logDAO.inserir(log);
    }

    public List<LogHistorico> listarTodos() throws SQLException {
        return logDAO.listarTodos();
    }

    public LogHistorico buscarPorId(int id) throws SQLException {
        return logDAO.buscarPorId(id);
    }

    public void atualizar(LogHistorico log) throws SQLException {
        logDAO.atualizar(log);
    }

    public void deletarPorId(int id) throws SQLException {
        logDAO.deletarPorId(id);
    }
}