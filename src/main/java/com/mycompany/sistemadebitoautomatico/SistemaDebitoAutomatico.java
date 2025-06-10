package com.mycompany.sistemadebitoautomatico;

import detectorfraude.util.ConexaoMySQL;
import java.sql.Connection;
import java.sql.SQLException;

public class SistemaDebitoAutomatico {

    public static void main(String[] args) {
        System.out.println("\n===== Teste ConexaoMySQL =====");
        try (Connection conexao = ConexaoMySQL.getConexao()) {
            System.out.println("Conexão realizada com sucesso!");
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
        }
    }
}
