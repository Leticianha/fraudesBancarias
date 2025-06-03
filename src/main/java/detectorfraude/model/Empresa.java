package detectorfraude.model;

public class Empresa {

    private int empresaId;
    private String nome;
    private String cnpj;
    private boolean cnpjValido;
    private String razaoSocial;
    private String situacaoCadastral;

    public Empresa() {}

    public int getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(int empresaId) {
        this.empresaId = empresaId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public boolean isCnpjValido() {
        return cnpjValido;
    }

    public void setCnpjValido(boolean cnpjValido) {
        this.cnpjValido = cnpjValido;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getSituacaoCadastral() {
        return situacaoCadastral;
    }

    public void setSituacaoCadastral(String situacaoCadastral) {
        this.situacaoCadastral = situacaoCadastral;
    }

    @Override
    public String toString() {
        return "Empresa{" +
                "empresaId=" + empresaId +
                ", nome='" + nome + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", cnpjValido=" + cnpjValido +
                ", razaoSocial='" + razaoSocial + '\'' +
                ", situacaoCadastral='" + situacaoCadastral + '\'' +
                '}';
    }
}