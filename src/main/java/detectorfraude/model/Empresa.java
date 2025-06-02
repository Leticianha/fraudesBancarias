package detectorfraude.model;

public class Empresa {
    private String razaoSocial;
    private String cnpj;
    private String situacaoCadastral;

    // Getters e Setters
    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getSituacaoCadastral() {
        return situacaoCadastral;
    }

    public void setSituacaoCadastral(String situacaoCadastral) {
        this.situacaoCadastral = situacaoCadastral;
    }

    public String getNome() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    public String getFantasia() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    public String getAtividadePrincipal() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}
