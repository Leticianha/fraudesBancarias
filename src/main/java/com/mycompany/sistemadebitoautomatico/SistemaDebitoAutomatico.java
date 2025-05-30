package com.mycompany.sistemadebitoautomatico;
import detectorfraude.model.ConexaoMySQL;
import java.sql.Connection;
import java.sql.SQLException;


public class SistemaDebitoAutomatico {
    public static void main(String[] args) {
        try (Connection conexao = ConexaoMySQL.getConexao()) {
            System.out.println("Conexão realizada com sucesso!");
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
        }
    }
}
