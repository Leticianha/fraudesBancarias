package detectorfraude.model;

import com.google.gson.annotations.SerializedName;

public class ApiResponse {

    @SerializedName("nome")
    private String nome;

    @SerializedName("cnpj")
    private String cnpj;

    @SerializedName("situacao")
    private String situacao;

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
}
