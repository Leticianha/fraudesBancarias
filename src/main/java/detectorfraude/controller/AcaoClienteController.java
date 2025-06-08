package detectorfraude.controller;

import detectorfraude.dao.AcaoClienteDAO;
import detectorfraude.model.AcaoCliente;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AcaoClienteController {

    private AcaoClienteDAO acaoDAO;

    public AcaoClienteController(Connection connection) {
        this.acaoDAO = new AcaoClienteDAO(connection);
    }

    public void inserir(AcaoCliente acao) throws SQLException {
        acaoDAO.inserir(acao);
    }

    public List<AcaoCliente> listarTodos() throws SQLException {
        return acaoDAO.listarTodos();
    }

    public AcaoCliente buscarPorId(int id) throws SQLException {
        return acaoDAO.buscarPorId(id);
    }

    public void atualizar(AcaoCliente acao) throws SQLException {
        acaoDAO.atualizar(acao);
    }

    public void deletarPorId(int id) throws SQLException {
        acaoDAO.deletarPorId(id);
    }
}
