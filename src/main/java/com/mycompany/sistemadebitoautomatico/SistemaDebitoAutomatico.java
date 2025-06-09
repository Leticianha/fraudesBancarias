package com.mycompany.sistemadebitoautomatico;

import detectorfraude.controller.DeteccaoFraudeController;
import detectorfraude.dao.AlertaDAO;
import detectorfraude.dao.ClienteDAO;
import detectorfraude.dao.DebitoAutomaticoDAO;
import detectorfraude.model.Alerta;
import detectorfraude.model.Cliente;
import detectorfraude.model.DebitoAutomatico;
import detectorfraude.model.StatusAlerta;
import detectorfraude.service.AlertaService;
import detectorfraude.service.SimilaridadeNomeService;
import detectorfraude.util.ConexaoMySQL;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class SistemaDebitoAutomatico {

    public static void main(String[] args) {
        testarSimilaridadeNomeService();
        testarAlertaService();
        testarClasseAlerta();
        testarAlertaDAO();
        testarDeteccaoFraudeController();
    }

    private static void testarSimilaridadeNomeService() {
        System.out.println("\n===== Teste SimilaridadeNomeService =====");
        SimilaridadeNomeService service = new SimilaridadeNomeService();

        String nome1 = "Feijãozinho";
        String nome2 = "Arrozinho Ltda";

        double indice = service.obterIndiceSimilaridade(nome1, nome2);
        boolean similares = service.nomesSaoSimilares(nome1, nome2);

        System.out.println("Índice de similaridade: " + indice);
        System.out.println("Nomes são semelhantes? " + similares);
    }

    private static void testarAlertaService() {
        System.out.println("\n===== Teste AlertaService =====");
        try (Connection connection = ConexaoMySQL.getConexao()) {

            // Buscando o cliente real do banco
            ClienteDAO clienteDAO = new ClienteDAO(connection);
            Cliente cliente = clienteDAO.buscarPorId(8759); // precisa existir no banco

            // Buscando o débito real do banco
            DebitoAutomaticoDAO debitoDAO = new DebitoAutomaticoDAO(connection);
            DebitoAutomatico debito = debitoDAO.buscarPorId(1); // precisa existir

            if (cliente != null && debito != null) {
                AlertaService service = new AlertaService(connection);
                service.gerarAlerta(debito, "Teste de alerta gerado via AlertaService", cliente);
                System.out.println("✅ Alerta com log gerado com sucesso.");
            } else {
                System.out.println("❌ Cliente ou débito não encontrados no banco.");
            }

        } catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    private static void testarClasseAlerta() {
        System.out.println("\n===== Teste Classe Alerta.java =====");

        Cliente cliente = new Cliente(9999, "Empresa Falsa", "000.000.000-00");
        DebitoAutomatico debito = new DebitoAutomatico();
        debito.setDebitoId(123);
        debito.setValor(new BigDecimal("45.00"));

        Alerta alerta = new Alerta();
        alerta.setAlertaId(999);
        alerta.setDebitoId(123);
        alerta.setDataAlerta(LocalDateTime.now());
        alerta.setMensagem("Teste de alerta falso");
        alerta.setStatus(StatusAlerta.Pendente);
        alerta.setCliente(cliente);
        alerta.setDebitoAutomatico(debito);

        System.out.println(alerta.toString());
    }

    private static void testarAlertaDAO() {
        System.out.println("\n===== Teste AlertaDAO =====");
        try (Connection connection = ConexaoMySQL.getConexao()) {
            AlertaDAO dao = new AlertaDAO(connection);

            Alerta alerta = new Alerta();
            alerta.setDebitoId(1); // ⚠️ Débito com ID 1 precisa existir
            alerta.setDataAlerta(LocalDateTime.now());
            alerta.setMensagem("Teste direto no AlertaDAO");
            alerta.setStatus(StatusAlerta.Pendente);

            int id = dao.inserir(alerta);
            System.out.println("✅ Inserido alerta com ID: " + id);

            Alerta buscado = dao.buscarPorId(id);
            System.out.println("🔎 Buscado: " + buscado.getMensagem());

            buscado.setMensagem("Mensagem atualizada");
            buscado.setStatus(StatusAlerta.Resolvido);
            dao.atualizar(buscado);
            System.out.println("✏️ Atualizado com sucesso.");

            dao.deletarPorId(id);
            System.out.println("🗑️ Deletado com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void testarDeteccaoFraudeController() {
        System.out.println("\n===== Teste DeteccaoFraudeController =====");
        DeteccaoFraudeController controller = new DeteccaoFraudeController();

        DebitoAutomatico debito = new DebitoAutomatico();
        debito.setDebitoId(1); // ⚠️ Certifique-se que esse ID existe no banco
        debito.setClienteId(8759);
        debito.setEmpresaId(2); // empresa com cnpj inválido
        debito.setValor(new BigDecimal("30.00"));
        debito.setTipoRecorrencia("Mensal");

        controller.processarDebito(debito, "Denunciar");
    }

}
