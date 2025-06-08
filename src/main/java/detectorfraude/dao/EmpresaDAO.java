package detectorfraude.dao;

import detectorfraude.model.Empresa;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpresaDAO {

    private Connection connection;

    public EmpresaDAO(Connection connection) {
        this.connection = connection;
    }

    public void inserir(Empresa empresa) throws SQLException {
        String sql = "INSERT INTO Empresa (nome, cnpj, cnpj_valido) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, empresa.getNome());
            stmt.setString(2, empresa.getCnpj());
            stmt.setBoolean(3, empresa.isCnpjValido());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro ao inserir empresa: " + e.getMessage(), e);
        }
    }

    public List<Empresa> listarTodas() throws SQLException {
        List<Empresa> empresas = new ArrayList<>();
        String sql = "SELECT * FROM Empresa";
        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Empresa e = new Empresa();
                e.setEmpresaId(rs.getInt("empresa_id"));
                e.setNome(rs.getString("nome"));
                e.setCnpj(rs.getString("cnpj"));
                e.setCnpjValido(rs.getBoolean("cnpj_valido"));
                empresas.add(e);
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao listar empresas: " + e.getMessage(), e);
        }
        return empresas;
    }

    public Empresa buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Empresa WHERE empresa_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Empresa e = new Empresa();
                    e.setEmpresaId(rs.getInt("empresa_id"));
                    e.setNome(rs.getString("nome"));
                    e.setCnpj(rs.getString("cnpj"));
                    e.setCnpjValido(rs.getBoolean("cnpj_valido"));
                    return e;
                }
            }
        }
        return null;
    }

    public void atualizar(Empresa empresa) throws SQLException {
        String sql = "UPDATE Empresa SET nome = ?, cnpj = ?, cnpj_valido = ? WHERE empresa_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, empresa.getNome());
            stmt.setString(2, empresa.getCnpj());
            stmt.setBoolean(3, empresa.isCnpjValido());
            stmt.setInt(4, empresa.getEmpresaId());
            stmt.executeUpdate();
        }
    }

    public void deletarPorId(int id) throws SQLException {
        String sql = "DELETE FROM Empresa WHERE empresa_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
