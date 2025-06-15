package com.mycompany.sistemadebitoautomatico;

import detectorfraude.controller.DeteccaoFraudeController;
import detectorfraude.dao.DebitoAutomaticoDAO;
import detectorfraude.model.DebitoAutomatico;
import detectorfraude.service.AnalisePadroesService;
import detectorfraude.util.ConexaoMySQL;

import java.sql.Connection;
import java.util.List;

public class SistemaDebitoAutomatico {

    public static void main(String[] args) {
        try (Connection connection = ConexaoMySQL.getConexao()) {

            // 1. Busca todos os débitos existentes no banco
            DebitoAutomaticoDAO dao = new DebitoAutomaticoDAO(connection);
            List<DebitoAutomatico> debitos = dao.listarTodos();

            if (debitos.isEmpty()) {
                System.out.println("❌ Nenhum débito automático cadastrado no banco.");
                return;
            }

            System.out.println("🔎 Iniciando análise de débitos automáticos...\n");

            // 2. Passa cada débito pelo processo de análise
            AnalisePadroesService analiseService = new AnalisePadroesService();
            DeteccaoFraudeController deteccaoController = new DeteccaoFraudeController();

            for (DebitoAutomatico debito : debitos) {
                System.out.println("🧾 Analisando débito ID " + debito.getDebitoId() + "...");
                boolean suspeito = analiseService.analisarDebito(debito);

                if (suspeito) {
                    // Simula ação do cliente após alerta
                    String acaoCliente = "Denunciar"; // Você pode testar "Bloquear" ou "Ignorar" também
                    deteccaoController.processarDebito(debito, acaoCliente);
                } else {
                    System.out.println("✅ Débito considerado normal.\n");
                }
            }

            System.out.println("✅ Análise concluída.");

        } catch (Exception e) {
            System.err.println("❌ Erro ao executar o sistema: " + e.getMessage());
        }
    }
}
