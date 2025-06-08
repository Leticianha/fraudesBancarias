package detectorfraude.dao;

import detectorfraude.model.DebitoAutomatico;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DebitoAutomaticoDAO {

    private Connection connection;

    public DebitoAutomaticoDAO(Connection connection) {
        this.connection = connection;
    }

    public void inserir(DebitoAutomatico debito) throws SQLException {
        String sql = "INSERT INTO Debito_Automatico (cliente_id, empresa_id, valor, data_cadastro, tipo_recorrencia, status_suspeita, status_ativo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, debito.getClienteId());
            stmt.setInt(2, debito.getEmpresaId());
            stmt.setBigDecimal(3, debito.getValor());
            stmt.setDate(4, Date.valueOf(debito.getDataCadastro()));
            stmt.setString(5, debito.getTipoRecorrencia());
            stmt.setString(6, debito.getStatusSuspeita());
            stmt.setString(7, debito.getStatusAtivo());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro ao inserir débito automático: " + e.getMessage(), e);
        }
    }

    public List<DebitoAutomatico> listarTodos() throws SQLException {
        List<DebitoAutomatico> debitos = new ArrayList<>();
        String sql = "SELECT * FROM Debito_Automatico";
        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                DebitoAutomatico d = new DebitoAutomatico();
                d.setDebitoId(rs.getInt("debito_id"));
                d.setClienteId(rs.getInt("cliente_id"));
                d.setEmpresaId(rs.getInt("empresa_id"));
                d.setValor(rs.getBigDecimal("valor"));
                d.setDataCadastro(rs.getDate("data_cadastro").toLocalDate());
                d.setTipoRecorrencia(rs.getString("tipo_recorrencia"));
                d.setStatusSuspeita(rs.getString("status_suspeita"));
                d.setStatusAtivo(rs.getString("status_ativo"));
                debitos.add(d);
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao listar débitos automáticos: " + e.getMessage(), e);
        }
        return debitos;
    }

    public DebitoAutomatico buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Debito_Automatico WHERE debito_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    DebitoAutomatico d = new DebitoAutomatico();
                    d.setDebitoId(rs.getInt("debito_id"));
                    d.setClienteId(rs.getInt("cliente_id"));
                    d.setEmpresaId(rs.getInt("empresa_id"));
                    d.setValor(rs.getBigDecimal("valor"));
                    d.setDataCadastro(rs.getDate("data_cadastro").toLocalDate());
                    d.setTipoRecorrencia(rs.getString("tipo_recorrencia"));
                    d.setStatusSuspeita(rs.getString("status_suspeita"));
                    d.setStatusAtivo(rs.getString("status_ativo"));
                    return d;
                }
            }
        }
        return null;
    }

    public void atualizar(DebitoAutomatico debito) throws SQLException {
        String sql = "UPDATE Debito_Automatico SET cliente_id = ?, empresa_id = ?, valor = ?, data_cadastro = ?, tipo_recorrencia = ?, status_suspeita = ?, status_ativo = ? WHERE debito_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, debito.getClienteId());
            stmt.setInt(2, debito.getEmpresaId());
            stmt.setBigDecimal(3, debito.getValor());
            stmt.setDate(4, Date.valueOf(debito.getDataCadastro()));
            stmt.setString(5, debito.getTipoRecorrencia());
            stmt.setString(6, debito.getStatusSuspeita());
            stmt.setString(7, debito.getStatusAtivo());
            stmt.setInt(8, debito.getDebitoId());
            stmt.executeUpdate();
        }
    }

    public void deletarPorId(int id) throws SQLException {
        String sql = "DELETE FROM Debito_Automatico WHERE debito_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
