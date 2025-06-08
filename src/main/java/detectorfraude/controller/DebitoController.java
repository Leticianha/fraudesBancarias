package detectorfraude.controller;

import detectorfraude.dao.DebitoAutomaticoDAO;
import detectorfraude.model.DebitoAutomatico;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DebitoController {

    private DebitoAutomaticoDAO debitoDAO;

    public DebitoController(Connection connection) {
        this.debitoDAO = new DebitoAutomaticoDAO(connection);
    }

    public void inserir(DebitoAutomatico debito) throws SQLException {
        debitoDAO.inserir(debito);
    }

    public List<DebitoAutomatico> listarTodos() throws SQLException {
        return debitoDAO.listarTodos();
    }

    public DebitoAutomatico buscarPorId(int id) throws SQLException {
        return debitoDAO.buscarPorId(id);
    }

    public void atualizar(DebitoAutomatico debito) throws SQLException {
        debitoDAO.atualizar(debito);
    }

    public void deletarPorId(int id) throws SQLException {
        debitoDAO.deletarPorId(id);
    }
}