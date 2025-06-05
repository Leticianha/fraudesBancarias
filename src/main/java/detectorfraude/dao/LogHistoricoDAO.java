package detectorfraude.dao;

import detectorfraude.model.LogHistorico;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LogHistoricoDAO {

    private Connection connection;

    public LogHistoricoDAO(Connection connection) {
        this.connection = connection;
    }

    public void inserir(LogHistorico log) throws SQLException {
        String sql = "INSERT INTO Log_Historico (cliente_id, descricao_evento, data_evento) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, log.getClienteId());
            stmt.setString(2, log.getDescricaoEvento());
            stmt.setTimestamp(3, Timestamp.valueOf(log.getDataEvento()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro ao inserir log histórico: " + e.getMessage(), e);
        }
    }

    public List<LogHistorico> listarTodos() throws SQLException {
        List<LogHistorico> logs = new ArrayList<>();
        String sql = "SELECT * FROM Log_Historico";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                LogHistorico l = new LogHistorico();
                l.setLogId(rs.getInt("log_id"));
                l.setClienteId(rs.getInt("cliente_id"));
                l.setDescricaoEvento(rs.getString("descricao_evento"));
                l.setDataEvento(rs.getTimestamp("data_evento").toLocalDateTime());
                logs.add(l);
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao listar logs históricos: " + e.getMessage(), e);
        }
        return logs;
    }
}