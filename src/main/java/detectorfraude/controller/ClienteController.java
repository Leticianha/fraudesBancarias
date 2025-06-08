package detectorfraude.controller;

import detectorfraude.dao.ClienteDAO;
import detectorfraude.model.Cliente;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ClienteController {

    private ClienteDAO clienteDAO;

    public ClienteController(Connection connection) {
        this.clienteDAO = new ClienteDAO(connection);
    }

    public void inserir(Cliente cliente) throws SQLException {
        clienteDAO.inserir(cliente);
    }

    public List<Cliente> listarTodos() throws SQLException {
        return clienteDAO.listarTodos();
    }

    public Cliente buscarPorId(int id) throws SQLException {
        return clienteDAO.buscarPorId(id);
    }

    public void atualizar(Cliente cliente) throws SQLException {
        clienteDAO.atualizar(cliente);
    }

    public void deletarPorId(int id) throws SQLException {
        clienteDAO.deletarPorId(id);
    }
}