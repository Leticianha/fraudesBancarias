package detectorfraude.model;

public class Empresa {

    private int empresaId;
    private String nome;
    private String cnpj;
    private boolean cnpjValido;
    private String razaoSocial;
    private String situacaoCadastral;
    private String nomeFantasia;
    private String naturezaJuridica;
    private String atividadeEconomica;
    private String endereco;
    private String tipoEmpresa;

    public Empresa() {
    }

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

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getNaturezaJuridica() {
        return naturezaJuridica;
    }

    public void setNaturezaJuridica(String naturezaJuridica) {
        this.naturezaJuridica = naturezaJuridica;
    }

    public String getAtividadeEconomica() {
        return atividadeEconomica;
    }

    public void setAtividadeEconomica(String atividadeEconomica) {
        this.atividadeEconomica = atividadeEconomica;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTipoEmpresa() {
        return tipoEmpresa;
    }

    public void setTipoEmpresa(String tipoEmpresa) {
        this.tipoEmpresa = tipoEmpresa;
    }

    public Empresa(int empresaId, String nome, String cnpj, boolean cnpjValido, String razaoSocial, String situacaoCadastral,
            String nomeFantasia, String naturezaJuridica, String atividadeEconomica, String endereco, String tipoEmpresa) {
        this.empresaId = empresaId;
        this.nome = nome;
        this.cnpj = cnpj;
        this.cnpjValido = cnpjValido;
        this.razaoSocial = razaoSocial;
        this.situacaoCadastral = situacaoCadastral;
        this.nomeFantasia = nomeFantasia;
        this.naturezaJuridica = naturezaJuridica;
        this.atividadeEconomica = atividadeEconomica;
        this.endereco = endereco;
        this.tipoEmpresa = tipoEmpresa;
    }

    @Override
    public String toString() {
        return "Empresa{"
                + "empresaId=" + empresaId
                + ", nome='" + nome + '\''
                + ", cnpj='" + cnpj + '\''
                + ", cnpjValido=" + cnpjValido
                + ", razaoSocial='" + razaoSocial + '\''
                + ", situacaoCadastral='" + situacaoCadastral + '\''
                + ", nomeFantasia='" + nomeFantasia + '\''
                + ", naturezaJuridica='" + naturezaJuridica + '\''
                + ", atividadeEconomica='" + atividadeEconomica + '\''
                + ", endereco='" + endereco + '\''
                + ", tipo='" + tipoEmpresa + '\''
                + '}';
    }
}
