package com.mycompany.sistemadebitoautomatico;

import detectorfraude.controller.ClienteController;
import detectorfraude.model.Cliente;
import detectorfraude.util.ConexaoMySQL;
import detectorfraude.view.TelaRoute;
import java.sql.Connection;


public class SistemaDebitoAutomatico {

    public static void main(String[] args) {
        try (Connection conn = ConexaoMySQL.getConexao()) {

            ClienteController clienteController = new ClienteController(conn);
            Cliente cliente = clienteController.buscarPorId(8759); // ou outro cliente do banco

            if (cliente != null) {
                new TelaRoute(cliente).setVisible(true);
            } else {
                System.out.println("Cliente não encontrado.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    } 
    }
