package detectorfraude.controller;

import detectorfraude.dao.AlertaDAO;
import detectorfraude.model.Alerta;
import detectorfraude.util.ConexaoMySQL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AlertaController {

    public void gerarAlerta(Alerta alerta) {
        try (Connection connection = ConexaoMySQL.getConexao()) {
            AlertaDAO dao = new AlertaDAO(connection);
            dao.inserir(alerta);
        } catch (SQLException e) {
            System.err.println("Erro ao gerar alerta: " + e.getMessage());
        }
    }

    public List<Alerta> listarAlertas() {
        try (Connection connection = ConexaoMySQL.getConexao()) {
            AlertaDAO dao = new AlertaDAO(connection);
            return dao.listarTodos();
        } catch (SQLException e) {
            System.err.println("Erro ao listar alertas: " + e.getMessage());
            return null;
        }
    }
}
