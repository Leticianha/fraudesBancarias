package detectorfraude.model;

import java.time.LocalDateTime;

public class AcaoCliente {

    private int acaoId;
    private int alertaId;
    private int clienteId;
    private String acao;
    private LocalDateTime dataAcao;

    // Getters e Setters

    public int getAcaoId() {
        return acaoId;
    }

    public void setAcaoId(int acaoId) {
        this.acaoId = acaoId;
    }

    public int getAlertaId() {
        return alertaId;
    }

    public void setAlertaId(int alertaId) {
        this.alertaId = alertaId;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public LocalDateTime getDataAcao() {
        return dataAcao;
    }

    public void setDataAcao(LocalDateTime dataAcao) {
        this.dataAcao = dataAcao;
    }
}