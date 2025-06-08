package detectorfraude.dao;

import detectorfraude.model.Alerta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlertaDAO {

    private Connection connection;

    public AlertaDAO(Connection connection) {
        this.connection = connection;
    }

    public int inserir(Alerta alerta) throws SQLException {
        String sql = "INSERT INTO Alerta (debito_id, data_alerta, mensagem, status_alerta) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, alerta.getDebitoId());
            stmt.setTimestamp(2, Timestamp.valueOf(alerta.getDataAlerta()));
            stmt.setString(3, alerta.getMensagem());
            stmt.setString(4, alerta.getStatusAlerta());
            stmt.executeUpdate();

            // Recupera o ID gerado
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int idGerado = rs.getInt(1);
                    alerta.setAlertaId(idGerado); // seta no objeto
                    return idGerado;
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
                a.setStatusAlerta(rs.getString("status_alerta"));
                alertas.add(a);
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao listar alertas: " + e.getMessage(), e);
        }
        return alertas;
    }
}
