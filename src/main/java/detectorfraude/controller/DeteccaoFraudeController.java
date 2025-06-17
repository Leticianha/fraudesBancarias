package detectorfraude.controller;

import detectorfraude.dao.AlertaDAO;
import detectorfraude.dao.LogHistoricoDAO;
import detectorfraude.dao.AcaoClienteDAO;
import detectorfraude.model.Alerta;
import detectorfraude.model.AcaoCliente;
import detectorfraude.model.DebitoAutomatico;
import detectorfraude.model.LogHistorico;
import detectorfraude.model.StatusAlerta;
import detectorfraude.service.AnalisePadroesService;
import detectorfraude.util.ConexaoMySQL;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class DeteccaoFraudeController {

    private AnalisePadroesService analiseService;

    public DeteccaoFraudeController() {
        this.analiseService = new AnalisePadroesService();
    }

    public void processarDebito(DebitoAutomatico debito, String acaoCliente) {
        List<String> motivos = analiseService.getMotivosSuspeita(debito);

        if (!motivos.isEmpty()) {
            String mensagemAlerta = String.join("\n", motivos);

            try (Connection connection = ConexaoMySQL.getConexao()) {

                // 1. Gerar Alerta
                Alerta alerta = new Alerta();
                alerta.setDebitoId(debito.getDebitoId());
                alerta.setDataAlerta(LocalDateTime.now());
                alerta.setMensagem(mensagemAlerta);
                alerta.setStatus(StatusAlerta.Pendente);

                AlertaDAO alertaDAO = new AlertaDAO(connection);
                int alertaId = alertaDAO.inserir(alerta);
                alerta.setAlertaId(alertaId);

                System.out.println("🔔 Alerta gerado:\n" + mensagemAlerta);

                // 2. Registrar Log
                LogHistorico log = new LogHistorico();
                log.setClienteId(debito.getClienteId());
                log.setDescricaoEvento("Alerta de débito suspeito:\n" + mensagemAlerta);
                log.setDataEvento(LocalDateTime.now());

                LogHistoricoDAO logDAO = new LogHistoricoDAO(connection);
                logDAO.inserir(log);
                System.out.println("📝 Evento registrado no histórico.");

                // 3. Registrar Ação do Cliente
                AcaoCliente acao = new AcaoCliente();
                acao.setAlertaId(alertaId);
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

    public String gerarResumoDebito(String nomeEmpresa, String cnpj, double valor, String data, String status) {
    return "DeteccaoFraudeController {\n" +
           "  Alerta gerado: 🔔\n" +
           "  - Empresa: " + nomeEmpresa + "\n" +
           "  - CNPJ: " + cnpj + "\n" +
           "  - Valor: R$ " + String.format("%.2f", valor) + "\n" +
           "  - Data: " + data + "\n" +
           "  Evento registrado no histórico: 📝\n" +
           "  - Débito suspeito registrado com sucesso\n" +
           "  Ação do cliente registrada: 📌\n" +
           "  - Tipo da ação: " + status + "\n" +
           "}";
}

}
