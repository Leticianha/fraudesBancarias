package com.mycompany.sistemadebitoautomatico;

import detectorfraude.model.Empresa;
import detectorfraude.model.DebitoAutomatico;
import detectorfraude.service.CnpjService;
import detectorfraude.util.FormatacaoUtil;
import detectorfraude.model.ConexaoMySQL;

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

        System.out.println("\n===== Teste Integração com a API =====");
        Scanner scanner = new Scanner(System.in);
        CnpjService cnpjService = new CnpjService();

        System.out.print("Digite o CNPJ: ");
        String cnpj = scanner.nextLine();

        Empresa empresa = cnpjService.consultarCnpj(cnpj);

        if (empresa != null) {
            System.out.println("Razão Social: " + empresa.getRazaoSocial());

            // Aqui formatamos o CNPJ na exibição
            String cnpjFormatado = FormatacaoUtil.formatarCnpj(empresa.getCnpj());
            System.out.println("CNPJ: " + cnpjFormatado);

            System.out.println("Situação Cadastral: " + empresa.getSituacaoCadastral());
        } else {
            System.out.println("CNPJ não encontrado ou inválido.");
        }

        System.out.println("\n===== Teste Classe Empresa =====");
        Empresa empresaTeste = new Empresa();
        empresaTeste.setEmpresaId(1);
        empresaTeste.setNome("Minha Empresa LTDA");
        empresaTeste.setCnpj("12345678000195");
        empresaTeste.setCnpjValido(true);
        empresaTeste.setRazaoSocial("Minha Empresa de Teste");
        empresaTeste.setSituacaoCadastral("ATIVA");

        System.out.println("Empresa ID: " + empresaTeste.getEmpresaId());
        System.out.println("Nome: " + empresaTeste.getNome());
        System.out.println("CNPJ: " + empresaTeste.getCnpj());
        System.out.println("CNPJ Válido: " + empresaTeste.isCnpjValido());
        System.out.println("Razão Social: " + empresaTeste.getRazaoSocial());
        System.out.println("Situação Cadastral: " + empresaTeste.getSituacaoCadastral());
        System.out.println("toString(): " + empresaTeste.toString());

        System.out.println("\n===== Teste Classe DebitoAutomatico =====");
        DebitoAutomatico debitoTeste = new DebitoAutomatico();
        debitoTeste.setDebitoId(100);
        debitoTeste.setEmpresaId(1);
        debitoTeste.setValor(BigDecimal.valueOf(500.00));
        debitoTeste.setDataAgendamento(LocalDate.now());
        debitoTeste.setStatus("PENDENTE");

        System.out.println("Débito ID: " + debitoTeste.getDebitoId());
        System.out.println("Empresa ID: " + debitoTeste.getEmpresaId());
        System.out.println("Valor: " + debitoTeste.getValor());
        System.out.println("Data Agendamento: " + debitoTeste.getDataAgendamento());
        System.out.println("Status: " + debitoTeste.getStatus());
        System.out.println("toString(): " + debitoTeste.toString());

        
        scanner.close();
    }
}
