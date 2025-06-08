package com.mycompany.sistemadebitoautomatico;

import detectorfraude.dao.*;
import detectorfraude.model.*;

import detectorfraude.util.ConexaoMySQL;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class SistemaDebitoAutomatico {

    public static void main(String[] args) {
        testarClienteDAO();
        testarEmpresaDAO();
        testarDebitoAutomaticoDAO();
        testarAlertaDAO();
        testarAcaoClienteDAO();
        testarLogHistoricoDAO();
    }

    public static void testarClienteDAO() {
        System.out.println("\n===== Teste ClienteDAO =====");
        try (Connection connection = ConexaoMySQL.getConexao()) {
            ClienteDAO dao = new ClienteDAO(connection);
            dao.deletarPorId(222); // Tenta deletar o cliente com ID 222 se já existir

            Cliente cliente = new Cliente(222, "Nat", "565.456.888-99");
            dao.inserir(cliente);

            Cliente buscado = dao.buscarPorId(222);
            System.out.println("Buscado: " + buscado.getNome());

            cliente.setNome("Malu Atualizada");
            dao.atualizar(cliente);

            List<Cliente> lista = dao.listarTodos();
            System.out.println("Clientes no banco: " + lista.size());

            DebitoAutomaticoDAO debitoDAO = new DebitoAutomaticoDAO(connection);
            List<DebitoAutomatico> debitos = debitoDAO.listarTodos();
            for (DebitoAutomatico d : debitos) {
                if (d.getClienteId() == cliente.getClienteId()) {
                    debitoDAO.deletarPorId(d.getDebitoId());
                }
            }

            dao.deletarPorId(222);
            System.out.println("Cliente deletado com sucesso.");

        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public static void testarEmpresaDAO() {
        System.out.println("\n===== Teste EmpresaDAO =====");
        try (Connection connection = ConexaoMySQL.getConexao()) {
            EmpresaDAO dao = new EmpresaDAO(connection);

            Empresa empresa = new Empresa(0, "Empresa Teste", "00000000000000", false);
            dao.inserir(empresa);

            List<Empresa> lista = dao.listarTodas();
            Empresa ultima = lista.get(lista.size() - 1);

            Empresa buscada = dao.buscarPorId(ultima.getEmpresaId());
            System.out.println("Buscada: " + buscada.getNome());

            buscada.setNome("Empresa Atualizada");
            dao.atualizar(buscada);

            dao.deletarPorId(buscada.getEmpresaId());
            System.out.println("Empresa deletada com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public static void testarDebitoAutomaticoDAO() {
        System.out.println("\n===== Teste DebitoAutomaticoDAO =====");
        try (Connection connection = ConexaoMySQL.getConexao()) {
            DebitoAutomaticoDAO dao = new DebitoAutomaticoDAO(connection);

            DebitoAutomatico debito = new DebitoAutomatico();
            debito.setClienteId(5467);
            debito.setEmpresaId(1);
            debito.setValor(new BigDecimal("10.50"));
            debito.setDataCadastro(LocalDate.now());
            debito.setTipoRecorrencia("Mensal");
            debito.setStatusSuspeita("Normal");
            debito.setStatusAtivo("Ativo");

            dao.inserir(debito);

            List<DebitoAutomatico> lista = dao.listarTodos();
            DebitoAutomatico ultimo = lista.get(lista.size() - 1);
            System.out.println("Último débito: " + ultimo.getValor());

            ultimo.setValor(new BigDecimal("99.99"));
            dao.atualizar(ultimo);

            dao.deletarPorId(ultimo.getDebitoId());
            System.out.println("Débito deletado com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public static void testarAlertaDAO() {
        System.out.println("\n===== Teste AlertaDAO =====");
        try (Connection connection = ConexaoMySQL.getConexao()) {
            AlertaDAO dao = new AlertaDAO(connection);

            Alerta alerta = new Alerta();
            alerta.setDebitoId(1);
            alerta.setDataAlerta(LocalDateTime.now());
            alerta.setMensagem("Teste de alerta");
            alerta.setStatusAlerta("Pendente");

            int idGerado = dao.inserir(alerta);
            alerta.setAlertaId(idGerado);

            Alerta buscado = dao.buscarPorId(idGerado);
            System.out.println("Alerta buscado: " + buscado.getMensagem());

            dao.deletarPorId(idGerado);
            System.out.println("Alerta deletado com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public static void testarAcaoClienteDAO() {
        System.out.println("\n===== Teste AcaoClienteDAO =====");
        try (Connection connection = ConexaoMySQL.getConexao()) {
            AcaoClienteDAO dao = new AcaoClienteDAO(connection);

            AcaoCliente acao = new AcaoCliente();
            acao.setAlertaId(1); // certifique-se que o alerta 1 existe
            acao.setClienteId(8759);
            acao.setAcao("Ignorar");
            acao.setDataAcao(LocalDateTime.now());

            dao.inserir(acao);

            List<AcaoCliente> lista = dao.listarTodos();
            AcaoCliente ultima = lista.get(lista.size() - 1);

            AcaoCliente buscada = dao.buscarPorId(ultima.getAcaoId());
            System.out.println("Ação buscada: " + buscada.getAcao());

            buscada.setAcao("Bloquear");
            dao.atualizar(buscada);

            dao.deletarPorId(buscada.getAcaoId());
            System.out.println("Ação deletada com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public static void testarLogHistoricoDAO() {
        System.out.println("\n===== Teste LogHistoricoDAO =====");
        try (Connection connection = ConexaoMySQL.getConexao()) {
            LogHistoricoDAO dao = new LogHistoricoDAO(connection);

            LogHistorico log = new LogHistorico();
            log.setClienteId(8759);
            log.setDescricaoEvento("Teste de log");
            log.setDataEvento(LocalDateTime.now());

            dao.inserir(log);

            List<LogHistorico> lista = dao.listarTodos();
            LogHistorico ultimo = lista.get(lista.size() - 1);

            LogHistorico buscado = dao.buscarPorId(ultimo.getLogId());
            System.out.println("Log buscado: " + buscado.getDescricaoEvento());

            buscado.setDescricaoEvento("Log Atualizado");
            dao.atualizar(buscado);

            dao.deletarPorId(buscado.getLogId());
            System.out.println("Log deletado com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
