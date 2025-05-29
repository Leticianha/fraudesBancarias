/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package detectorfraude.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Leticia
 */
public class ConexaoMySQL {
    // 1. Constantes de configuração
    private static final String URL = "jdbc:mysql://localhost:3306/SistemaDebitoAutomatico";
    private static final String USUARIO = "root";
    private static final String SENHA = "java";

    // 2. Método para abrir a conexão
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        try {
            // Carrega o driver do MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Estabelece a conexão
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (ClassNotFoundException e) {
            System.err.println("Driver MySQL não encontrado: " + e.getMessage());
            throw e; // Propaga exceção
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            throw e; // Propaga exceção
        }
}

// 3. Método para fechar a conexão
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Conexão fechada com sucesso.");
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }
}
