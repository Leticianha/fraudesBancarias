package detectorfraude.controller;

import detectorfraude.dao.ClienteDAO;
import detectorfraude.model.Cliente;
import detectorfraude.util.ConexaoMySQL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ClienteController {

    public void cadastrarCliente(Cliente cliente) {
        try (Connection connection = ConexaoMySQL.getConexao()) {
            ClienteDAO dao = new ClienteDAO(connection);
            dao.inserir(cliente);
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    public List<Cliente> listarClientes() {
        try (Connection connection = ConexaoMySQL.getConexao()) {
            ClienteDAO dao = new ClienteDAO(connection);
            return dao.listarTodos();
        } catch (SQLException e) {
            System.err.println("Erro ao listar clientes: " + e.getMessage());
            return null;
        }
    }
}
