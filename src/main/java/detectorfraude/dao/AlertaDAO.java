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
        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

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

    public Alerta buscarPorId(int alertaId) throws SQLException {
        String sql = "SELECT * FROM Alerta WHERE alerta_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, alertaId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Alerta alerta = new Alerta();
                    alerta.setAlertaId(rs.getInt("alerta_id"));
                    alerta.setDebitoId(rs.getInt("debito_id"));
                    alerta.setDataAlerta(rs.getTimestamp("data_alerta").toLocalDateTime());
                    alerta.setMensagem(rs.getString("mensagem"));
                    alerta.setStatus(StatusAlerta.valueOf(rs.getString("status_alerta")));
                    return alerta;
                }
            }
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

    public void atualizarStatus(int alertaId, StatusAlerta statusAlerta) throws SQLException {
        String sql = "UPDATE Alerta SET status_alerta = ? WHERE alerta_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, statusAlerta.name()); // Usa o nome do enum como texto
            stmt.setInt(2, alertaId);
            stmt.executeUpdate();
        }
    }

    public int buscarClienteIdPorAlertaId(int alertaId) throws SQLException {
        String sql = "SELECT c.cliente_id FROM Alerta a "
                + "JOIN Debito_Automatico d ON a.debito_id = d.debito_id "
                + "JOIN Cliente c ON d.cliente_id = c.cliente_id "
                + "WHERE a.alerta_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, alertaId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("cliente_id");
                } else {
                    throw new SQLException("Cliente não encontrado para o alerta " + alertaId);
                }
            }
        }
    }

    public Alerta buscarPorDebitoId(int debitoId) throws SQLException {
        String sql = "SELECT * FROM Alerta WHERE debito_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, debitoId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Alerta alerta = new Alerta();
                    alerta.setAlertaId(rs.getInt("alerta_id"));
                    alerta.setDebitoId(rs.getInt("debito_id"));
                    alerta.setDataAlerta(rs.getTimestamp("data_alerta").toLocalDateTime());
                    alerta.setMensagem(rs.getString("mensagem"));
                    alerta.setStatus(StatusAlerta.valueOf(rs.getString("status_alerta")));
                    return alerta;
                }
            }
        }

        return null;
    }

}
