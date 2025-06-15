package detectorfraude.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class APIResponse {

    @SerializedName("nome")
    private String nome;

    @SerializedName("cnpj")
    private String cnpj;

    @SerializedName("situacao")
    private String situacao;

    @SerializedName("fantasia")
    private String fantasia;

    @SerializedName("natureza_juridica")
    private String naturezaJuridica;

    @SerializedName("atividade_principal")
    private List<Atividade> atividadePrincipal;

    @SerializedName("logradouro")
    private String logradouro;

    @SerializedName("numero")
    private String numero;

    @SerializedName("bairro")
    private String bairro;

    @SerializedName("municipio")
    private String municipio;

    @SerializedName("uf")
    private String uf;

    @SerializedName("cep")
    private String cep;

    @SerializedName("tipo")
    private String tipo;

    // Getters e Setters
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

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getFantasia() {
        return fantasia;
    }

    public void setFantasia(String fantasia) {
        this.fantasia = fantasia;
    }

    public String getNaturezaJuridica() {
        return naturezaJuridica;
    }

    public void setNaturezaJuridica(String naturezaJuridica) {
        this.naturezaJuridica = naturezaJuridica;
    }

    public List<Atividade> getAtividadePrincipal() {
        return atividadePrincipal;
    }

    public void setAtividadePrincipal(List<Atividade> atividadePrincipal) {
        this.atividadePrincipal = atividadePrincipal;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
