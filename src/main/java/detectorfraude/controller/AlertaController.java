package detectorfraude.controller;

import detectorfraude.dao.AlertaDAO;
import detectorfraude.model.Alerta;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AlertaController {

    private AlertaDAO alertaDAO;

    public AlertaController(Connection connection) {
        this.alertaDAO = new AlertaDAO(connection);
    }

    public int inserir(Alerta alerta) throws SQLException {
        return alertaDAO.inserir(alerta);
    }

    public List<Alerta> listarTodos() throws SQLException {
        return alertaDAO.listarTodos();
    }

    public Alerta buscarPorId(int id) throws SQLException {
        return alertaDAO.buscarPorId(id);
    }

    public void deletarPorId(int id) throws SQLException {
        alertaDAO.deletarPorId(id);
    }
}