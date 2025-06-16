package detectorfraude.model;

import java.util.Date;

public class ExtratoDTO {
    private String nomeEmpresa;
    private String cnpj;
    private double valor;
    private Date data;
    private String statusAcao;

    public ExtratoDTO(String nomeEmpresa, String cnpj, double valor, Date data, String statusAcao) {
        this.nomeEmpresa = nomeEmpresa;
        this.cnpj = cnpj;
        this.valor = valor;
        this.data = data;
        this.statusAcao = statusAcao;
    }

    public String getNomeEmpresa() { return nomeEmpresa; }
    public String getCnpj() { return cnpj; }
    public double getValor() { return valor; }
    public Date getData() { return data; }
    public String getStatusAcao() { return statusAcao; }
}
