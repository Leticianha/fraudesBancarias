package detectorfraude.controller;

import detectorfraude.dao.AlertaDAO;
import detectorfraude.dao.LogHistoricoDAO;
import detectorfraude.dao.AcaoClienteDAO;
import detectorfraude.dao.EmpresaDAO;
import detectorfraude.model.Alerta;
import detectorfraude.model.AcaoCliente;
import detectorfraude.model.DebitoAutomatico;
import detectorfraude.model.Empresa;
import detectorfraude.model.LogHistorico;
import detectorfraude.model.StatusAlerta;
import detectorfraude.service.AnalisePadroesService;
import detectorfraude.service.ValidacaoCNPJService;
import detectorfraude.util.ConexaoMySQL;
import java.math.BigDecimal;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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

    public int obterEmpresaIdPorCnpj(String cnpj) {
        try (Connection conn = ConexaoMySQL.getConexao()) {
            EmpresaDAO empresaDAO = new EmpresaDAO(conn);
            List<Empresa> empresas = empresaDAO.listarTodas();
            for (Empresa emp : empresas) {
                if (emp.getCnpj().equals(cnpj)) {
                    return emp.getEmpresaId();
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar empresa por CNPJ: " + e.getMessage());
        }
        return -1; // se não encontrou
    }

    public String gerarResumoDebito(String nomeEmpresa, String cnpj, BigDecimal valor, String data, String status) {
        StringBuilder resumo = new StringBuilder();

        resumo.append("Empresa: ").append(nomeEmpresa).append("\n");
        resumo.append("CNPJ: ").append(cnpj).append("\n");
        resumo.append("Valor: R$ ").append(String.format("%.2f", valor)).append("\n");
        resumo.append("Data: ").append(data).append("\n");
        resumo.append("Status: ").append(status).append("\n");

        try {
            // Criação do débito com os dados básicos
            DebitoAutomatico debito = new DebitoAutomatico();
            debito.setValor(valor);
            debito.setData(new SimpleDateFormat("dd/MM/yyyy").parse(data));
            debito.setEmpresaId(obterEmpresaIdPorCnpj(cnpj));

            // Chamada à análise de padrões
            AnalisePadroesService padroes = new AnalisePadroesService();
            List<String> motivos = padroes.getMotivosSuspeita(debito);

            if (!motivos.isEmpty()) {
                resumo.append("Motivos para marcação como suspeito:\n");
                for (String motivo : motivos) {
                    resumo.append("- ").append(motivo).append("\n");
                }
            } else {
                resumo.append("Nenhum padrão suspeito identificado.\n");
            }

        } catch (Exception e) {
            resumo.append("Erro ao analisar débito: ").append(e.getMessage()).append("\n");
        }

        resumo.append("------------------------------------------------------------\n");
        return resumo.toString();
    }
}
