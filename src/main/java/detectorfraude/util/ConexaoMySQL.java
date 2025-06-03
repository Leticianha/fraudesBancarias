package detectorfraude.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoMySQL {

    private static final String URL = System.getenv("DB_URL");
    private static final String USER = System.getenv("DB_USER");
    private static final String PASSWORD = System.getenv("DB_PASSWORD");

    /*
    setx DB_URL "jdbc:mysql://127.0.0.1:3306/fraude_bancaria?useTimezone=true&serverTimezone=UTC"
    setx DB_USER "root"
    setx DB_PASSWORD "sua_senha"
     */
    
    public static Connection getConexao() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException ex) {
            throw new SQLException("Driver do banco de dados não localizado", ex);
        } catch (NullPointerException ex) {
            throw new SQLException("Variáveis de ambiente DB_URL, DB_USER ou DB_PASSWORD não configuradas.", ex);
        }
    }
}
