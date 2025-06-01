package com.mycompany.sistemadebitoautomatico;

import detectorfraude.model.Empresa;
import detectorfraude.service.CnpjService;
import detectorfraude.util.FormatacaoUtil;
import detectorfraude.model.ConexaoMySQL;
import java.sql.Connection;
import java.sql.SQLException;

import java.util.Scanner;

public class SistemaDebitoAutomatico {

    public static void main(String[] args) {
        try (Connection conexao = ConexaoMySQL.getConexao()) {
            System.out.println("Conexão realizada com sucesso!");
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
        }

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

        scanner.close();
    }
}
