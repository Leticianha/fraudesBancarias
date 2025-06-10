package com.mycompany.sistemadebitoautomatico;

import detectorfraude.controller.DeteccaoFraudeController;
import detectorfraude.model.DebitoAutomatico;
import detectorfraude.model.Empresa;
import detectorfraude.service.AnalisePadroesService;
import detectorfraude.service.CnpjService;
import detectorfraude.service.ValidacaoCNPJService;
import java.math.BigDecimal;
import java.util.List;

public class SistemaDebitoAutomatico {

    public static void main(String[] args) {
        testarValidacaoCNPJService();
        testarCnpjService();
        testarAnalisePadroesService();
        testarDeteccaoFraudeController();
    }

    private static void testarValidacaoCNPJService() {
        System.out.println("\n===== Teste ValidacaoCNPJService =====");
        ValidacaoCNPJService validador = new ValidacaoCNPJService();

        String cnpjValido = "00623904000173";
        String cnpjInvalido = "11111111111111";

        System.out.println("CNPJ válido? " + cnpjValido + ": " + validador.validarCNPJ(cnpjValido));
        System.out.println("CNPJ válido? " + cnpjInvalido + ": " + validador.validarCNPJ(cnpjInvalido));
    }

    private static void testarCnpjService() {
        System.out.println("\n===== Teste CnpjService =====");
        CnpjService service = new CnpjService();

        String cnpj = "27865757000102"; // Exemplo válido da Natura
        Empresa empresa = service.consultarCnpj(cnpj);

        if (empresa != null) {
            System.out.println("✔️ Empresa encontrada:");
            System.out.println("Razão Social: " + empresa.getRazaoSocial());
            System.out.println("CNPJ: " + empresa.getCnpj());
            System.out.println("Situação: " + empresa.getSituacaoCadastral());
        } else {
            System.out.println("❌ Não foi possível obter os dados do CNPJ.");
        }
    }

    private static void testarAnalisePadroesService() {
        System.out.println("\n===== Teste AnalisePadroesService =====");
        AnalisePadroesService service = new AnalisePadroesService();

        DebitoAutomatico debito = new DebitoAutomatico();
        debito.setClienteId(5467);
        debito.setEmpresaId(2); // empresa inválida no banco
        debito.setValor(new BigDecimal("20.00"));
        debito.setTipoRecorrencia("Mensal");

        List<String> motivos = service.getMotivosSuspeita(debito);

        if (!motivos.isEmpty()) {
            System.out.println("⚠️ Débito suspeito por:");
            motivos.forEach(System.out::println);
        } else {
            System.out.println("✅ Débito considerado normal.");
        }
    }

    private static void testarDeteccaoFraudeController() {
        System.out.println("\n===== Teste DeteccaoFraudeController =====");
        DeteccaoFraudeController controller = new DeteccaoFraudeController();

        DebitoAutomatico debito = new DebitoAutomatico();
        debito.setDebitoId(1); // precisa existir no banco!
        debito.setClienteId(8759);
        debito.setEmpresaId(2); // empresa inválida no banco
        debito.setValor(new BigDecimal("20.00"));
        debito.setTipoRecorrencia("Mensal");

        controller.processarDebito(debito, "Denunciar");
    }

}
