package com.mycompany.sistemadebitoautomatico;

import detectorfraude.model.Empresa;
import detectorfraude.model.DebitoAutomatico;
import detectorfraude.service.CnpjService;
import detectorfraude.util.FormatacaoUtil;
import detectorfraude.util.ConexaoMySQL;
import detectorfraude.controller.*;
import detectorfraude.model.*;
import java.time.LocalDateTime;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.Scanner;

public class SistemaDebitoAutomatico {

    public static void main(String[] args) {
        System.out.println("\n===== Teste ConexaoMySQL =====");
        try (Connection conexao = ConexaoMySQL.getConexao()) {
            System.out.println("Conexão realizada com sucesso!");
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
        }

        testarClienteController();
        testarEmpresaController();
        testarDebitoController();
        testarAlertaController();
        testarAcaoClienteController();
        testarLogHistoricoController();
    }

    private static void testarClienteController() {
        System.out.println("\n===== Teste ClienteController =====");
        try (Connection connection = ConexaoMySQL.getConexao()) {
            ClienteController controller = new ClienteController(connection);
            int id = (int) (Math.random() * 9000 + 1000);

            Cliente cliente = new Cliente(id, "Nat", "555.111.222-33");
            controller.inserir(cliente);

            Cliente buscado = controller.buscarPorId(id);
            System.out.println("Buscado: " + buscado.getNome());

            cliente.setNome("Nat Atualizada");
            controller.atualizar(cliente);

            controller.listarTodos().forEach(c -> System.out.println("Cliente: " + c.getNome()));

            /*controller.deletarPorId(id);*/
            System.out.println("Cliente deletado com sucesso.");;
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void testarEmpresaController() {
        System.out.println("\n===== Teste EmpresaController =====");
        try (Connection connection = ConexaoMySQL.getConexao()) {
            EmpresaController controller = new EmpresaController(connection);

            Empresa empresa = new Empresa();
            empresa.setNome("Empresa Swing");
            empresa.setCnpj("00000000000000");
            empresa.setCnpjValido(true);
            controller.inserir(empresa);

            Empresa ultima = controller.listarTodas().getLast();
            Empresa buscada = controller.buscarPorId(ultima.getEmpresaId());
            System.out.println("Buscada: " + buscada.getNome());

            buscada.setNome("Empresa Swing Atualizada");
            controller.atualizar(buscada);

            controller.deletarPorId(buscada.getEmpresaId());
            System.out.println("Empresa deletada com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void testarDebitoController() {
        System.out.println("\n===== Teste DebitoController =====");
        try (Connection connection = ConexaoMySQL.getConexao()) {
            DebitoController controller = new DebitoController(connection);

            DebitoAutomatico d = new DebitoAutomatico();
            d.setClienteId(5467);
            d.setEmpresaId(1);
            d.setValor(new BigDecimal("49.99"));
            d.setDataCadastro(LocalDate.now());
            d.setTipoRecorrencia("Mensal");
            d.setStatusSuspeita("Normal");
            d.setStatusAtivo("Ativo");
            controller.inserir(d);

            DebitoAutomatico ultimo = controller.listarTodos().getLast();
            ultimo.setValor(new BigDecimal("99.99"));
            controller.atualizar(ultimo);

            controller.deletarPorId(ultimo.getDebitoId());
            System.out.println("Débito deletado com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void testarAlertaController() {
        System.out.println("\n===== Teste AlertaController =====");
        try (Connection connection = ConexaoMySQL.getConexao()) {
            AlertaController controller = new AlertaController(connection);

            Alerta alerta = new Alerta();
            alerta.setDebitoId(1); // certifique-se de que exista
            alerta.setDataAlerta(LocalDateTime.now());
            alerta.setMensagem("Alerta Teste");
            alerta.setStatusAlerta("Pendente");

            int id = controller.inserir(alerta);
            Alerta buscado = controller.buscarPorId(id);
            System.out.println("Mensagem do alerta: " + buscado.getMensagem());

            controller.deletarPorId(id);
            System.out.println("Alerta deletado com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void testarAcaoClienteController() {
        System.out.println("\n===== Teste AcaoClienteController =====");
        try (Connection connection = ConexaoMySQL.getConexao()) {
            AcaoClienteController controller = new AcaoClienteController(connection);

            AcaoCliente acao = new AcaoCliente();
            acao.setAlertaId(1); // alerta 1 precisa existir
            acao.setClienteId(96); // cliente 8759 precisa existir
            acao.setAcao("Ignorar");
            acao.setDataAcao(LocalDateTime.now());
            controller.inserir(acao);

            AcaoCliente ultima = controller.listarTodos().getLast();
            ultima.setAcao("Bloquear");
            controller.atualizar(ultima);

            controller.deletarPorId(ultima.getAcaoId());
            System.out.println("Ação deletada com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void testarLogHistoricoController() {
        System.out.println("\n===== Teste LogHistoricoController =====");
        try (Connection connection = ConexaoMySQL.getConexao()) {
            LogHistoricoController controller = new LogHistoricoController(connection);

            LogHistorico log = new LogHistorico();
            log.setClienteId(8759);
            log.setDescricaoEvento("Teste Controller");
            log.setDataEvento(LocalDateTime.now());
            controller.inserir(log);

            LogHistorico ultimo = controller.listarTodos().getLast();
            ultimo.setDescricaoEvento("Log Atualizado");
            controller.atualizar(ultimo);

            controller.deletarPorId(ultimo.getLogId());
            System.out.println("Log deletado com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

}
