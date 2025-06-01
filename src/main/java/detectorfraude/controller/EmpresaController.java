package detectorfraude.controller;

import detectorfraude.model.Empresa;
import detectorfraude.service.CnpjService;

public class EmpresaController {
    private CnpjService cnpjService;

    public EmpresaController() {
        this.cnpjService = new CnpjService();
    }

    public Empresa consultarEmpresaPorCnpj(String cnpj) {
        return cnpjService.consultarCnpj(cnpj);
    }
}
