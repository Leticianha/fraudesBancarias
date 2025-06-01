package detectorfraude.controller;

import detectorfraude.model.Empresa;

public class IntegracaoAPIController {

    public void testarConsultaCnpj(String cnpj) {
        EmpresaController empresaController = new EmpresaController();
        Empresa empresa = empresaController.consultarEmpresaPorCnpj(cnpj);

        if (empresa != null) {
            System.out.println("Razão Social: " + empresa.getRazaoSocial());
            System.out.println("CNPJ: " + empresa.getCnpj());
            System.out.println("Situação Cadastral: " + empresa.getSituacaoCadastral());
        } else {
            System.out.println("CNPJ não encontrado ou erro na consulta.");
        }
    }
}
