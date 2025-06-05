package detectorfraude.dao;

import detectorfraude.model.AcaoCliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AcaoClienteDAO {

    private Connection connection;

    public AcaoClienteDAO(Connection connection) {
        this.connection = connection;
    }

    public void inserir(AcaoCliente acao) throws SQLException {
        String sql = "INSERT INTO Acao_Cliente (alerta_id, cliente_id, acao, data_acao) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, acao.getAlertaId());
            stmt.setInt(2, acao.getClienteId());
            stmt.setString(3, acao.getAcao());
            stmt.setTimestamp(4, Timestamp.valueOf(acao.getDataAcao()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro ao inserir ação do cliente: " + e.getMessage(), e);
        }
    }

    public List<AcaoCliente> listarTodos() throws SQLException {
        List<AcaoCliente> acoes = new ArrayList<>();
        String sql = "SELECT * FROM Acao_Cliente";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                AcaoCliente a = new AcaoCliente();
                a.setAcaoId(rs.getInt("acao_id"));
                a.setAlertaId(rs.getInt("alerta_id"));
                a.setClienteId(rs.getInt("cliente_id"));
                a.setAcao(rs.getString("acao"));
                a.setDataAcao(rs.getTimestamp("data_acao").toLocalDateTime());
                acoes.add(a);
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao listar ações do cliente: " + e.getMessage(), e);
        }
        return acoes;
    }
}
