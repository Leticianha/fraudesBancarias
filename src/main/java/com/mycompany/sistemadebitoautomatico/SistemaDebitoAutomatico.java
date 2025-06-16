package com.mycompany.sistemadebitoautomatico;

import detectorfraude.controller.ClienteController;
import detectorfraude.model.Cliente;
import detectorfraude.util.ConexaoMySQL;
import detectorfraude.view.TelaRoute;
import java.sql.Connection;

public class SistemaDebitoAutomatico {

    public static void main(String[] args) {
        Connection conn = null;
        try {
            conn = ConexaoMySQL.getConexao();

            ClienteController clienteController = new ClienteController(conn);
            Cliente cliente = clienteController.buscarPorId(96);

            if (cliente != null) {
                new TelaRoute(cliente, conn).setVisible(true);
            } else {
                System.out.println("Cliente não encontrado.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Não feche aqui — o ideal é fechar a conexão quando o sistema for encerrado
        // Você pode usar um listener para isso, por exemplo, quando a janela for fechada
    }
}
