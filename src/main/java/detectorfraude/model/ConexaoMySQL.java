package detectorfraude.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoMySQL {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/fraude_bancaria?useTimezone=true&serverTimezone=UTC";
    private static final String USER = "usuario";
    private static final String PASSWORD = "senha";

    public static Connection getConexao() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException ex) {
            throw new SQLException("Driver do banco de dados não localizado", ex);
        }
    }
}
