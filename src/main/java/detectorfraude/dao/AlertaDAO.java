package detectorfraude.dao;

import detectorfraude.model.Alerta;
import detectorfraude.model.StatusAlerta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlertaDAO {

    private final Connection connection;

    public AlertaDAO(Connection connection) {
        this.connection = connection;
    }

    public int inserir(Alerta alerta) throws SQLException {
        String sql = "INSERT INTO Alerta (debito_id, data_alerta, mensagem, status_alerta) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, alerta.getDebitoId());
            stmt.setTimestamp(2, Timestamp.valueOf(alerta.getDataAlerta()));
            stmt.setString(3, alerta.getMensagem());
            stmt.setString(4, alerta.getStatus().name()); // enum → string
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // retorna o ID gerado
                }
            }

        } catch (SQLException e) {
            throw new SQLException("Erro ao inserir alerta: " + e.getMessage(), e);
        }
        return -1;
    }

    public List<Alerta> listarTodos() throws SQLException {
        List<Alerta> alertas = new ArrayList<>();
        String sql = "SELECT * FROM Alerta";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Alerta a = new Alerta();
                a.setAlertaId(rs.getInt("alerta_id"));
                a.setDebitoId(rs.getInt("debito_id"));
                a.setDataAlerta(rs.getTimestamp("data_alerta").toLocalDateTime());
                a.setMensagem(rs.getString("mensagem"));
                a.setStatus(StatusAlerta.valueOf(rs.getString("status_alerta"))); // string → enum
                alertas.add(a);
            }

        } catch (SQLException e) {
            throw new SQLException("Erro ao listar alertas: " + e.getMessage(), e);
        }
        return alertas;
    }

    public Alerta buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Alerta WHERE alerta_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Alerta a = new Alerta();
                    a.setAlertaId(rs.getInt("alerta_id"));
                    a.setDebitoId(rs.getInt("debito_id"));
                    a.setDataAlerta(rs.getTimestamp("data_alerta").toLocalDateTime());
                    a.setMensagem(rs.getString("mensagem"));
                    a.setStatus(StatusAlerta.valueOf(rs.getString("status_alerta")));
                    return a;
                }
            }

        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar alerta: " + e.getMessage(), e);
        }

        return null;
    }

    public List<Alerta> buscarPorClienteId(int clienteId) throws SQLException {
        List<Alerta> alertas = new ArrayList<>();
        String sql = """
                SELECT a.*
                FROM Alerta a
                JOIN Debito_Automatico d ON a.debito_id = d.debito_id
                WHERE d.cliente_id = ?
                """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, clienteId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Alerta a = new Alerta();
                    a.setAlertaId(rs.getInt("alerta_id"));
                    a.setDebitoId(rs.getInt("debito_id"));
                    a.setDataAlerta(rs.getTimestamp("data_alerta").toLocalDateTime());
                    a.setMensagem(rs.getString("mensagem"));
                    a.setStatus(StatusAlerta.valueOf(rs.getString("status_alerta")));
                    alertas.add(a);
                }
            }

        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar alertas do cliente: " + e.getMessage(), e);
        }

        return alertas;
    }

    public void deletarPorId(int id) throws SQLException {
        String sql = "DELETE FROM Alerta WHERE alerta_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro ao deletar alerta: " + e.getMessage(), e);
        }
    }

    public void atualizar(Alerta alerta) throws SQLException {
        String sql = "UPDATE Alerta SET mensagem = ?, status_alerta = ? WHERE alerta_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, alerta.getMensagem());
            stmt.setString(2, alerta.getStatus().name());
            stmt.setInt(3, alerta.getAlertaId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro ao atualizar alerta: " + e.getMessage(), e);
        }
    }
}
