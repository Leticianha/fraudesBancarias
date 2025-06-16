package detectorfraude.dao;

import detectorfraude.model.ExtratoDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ExtratoDAO {

    public List<ExtratoDTO> listarExtratoPorCliente(int clienteId, Connection conn) {
        List<ExtratoDTO> lista = new ArrayList<>();

        String sql = """
            SELECT e.nome AS empresa_nome,
                   e.cnpj,
                   d.valor,
                   d.data_cadastro,
                   d.status_acao
            FROM Debito_Automatico d
            INNER JOIN Empresa e ON d.empresa_id = e.empresa_id
            WHERE d.cliente_id = ?
            ORDER BY d.data_cadastro DESC
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, clienteId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ExtratoDTO dto = new ExtratoDTO(
                    rs.getString("empresa_nome"),
                    rs.getString("cnpj"),
                    rs.getDouble("valor"),
                    rs.getDate("data_cadastro"),
                    rs.getString("status_acao")
                );
                lista.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}
