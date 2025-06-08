package detectorfraude.controller;

import detectorfraude.dao.AlertaDAO;
import detectorfraude.dao.LogHistoricoDAO;
import detectorfraude.dao.AcaoClienteDAO;
import detectorfraude.model.Alerta;
import detectorfraude.model.AcaoCliente;
import detectorfraude.model.DebitoAutomatico;
import detectorfraude.model.LogHistorico;
import detectorfraude.service.AnalisePadroesService;
import detectorfraude.util.ConexaoMySQL;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class DeteccaoFraudeController {

    private AnalisePadroesService analiseService;

    public DeteccaoFraudeController() {
        this.analiseService = new AnalisePadroesService();
    }

    public void processarDebito(DebitoAutomatico debito, String acaoCliente) {
        boolean suspeito = analiseService.analisarDebito(debito);

        if (suspeito) {
            try (Connection connection = ConexaoMySQL.getConexao()) {

                // 1. Gerar Alerta
                Alerta alerta = new Alerta();
                alerta.setDebitoId(debito.getDebitoId());
                alerta.setDataAlerta(LocalDateTime.now());
                alerta.setMensagem("Débito suspeito detectado");
                alerta.setStatusAlerta("Pendente");

                AlertaDAO alertaDAO = new AlertaDAO(connection);
                int alertaId = alertaDAO.inserir(alerta);         // <-- Aqui pega o ID gerado
                alerta.setAlertaId(alertaId);                     // <-- Atualiza o objeto alerta
                System.out.println("🔔 Alerta gerado com sucesso!");

                // 2. Registrar Log
                LogHistorico log = new LogHistorico();
                log.setClienteId(debito.getClienteId());
                log.setDescricaoEvento("Débito suspeito detectado e alerta gerado.");
                log.setDataEvento(LocalDateTime.now());

                LogHistoricoDAO logDAO = new LogHistoricoDAO(connection);
                logDAO.inserir(log);
                System.out.println("📝 Evento registrado no histórico.");

                // 3. Registrar Ação do Cliente
                AcaoCliente acao = new AcaoCliente();
                acao.setAlertaId(alerta.getAlertaId()); // ⚠️ É necessário atualizar esse ID
                acao.setClienteId(debito.getClienteId());
                acao.setAcao(acaoCliente); // "Denunciar", "Bloquear" ou "Ignorar"
                acao.setDataAcao(LocalDateTime.now());

                AcaoClienteDAO acaoDAO = new AcaoClienteDAO(connection);
                acaoDAO.inserir(acao);
                System.out.println("📌 Ação do cliente registrada: " + acaoCliente);

            } catch (SQLException e) {
                System.err.println("Erro ao processar débito suspeito: " + e.getMessage());
            }
        } else {
            System.out.println("✅ Débito considerado normal.");
        }
    }
}
