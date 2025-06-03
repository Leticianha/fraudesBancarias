package detectorfraude.model;

import java.time.LocalDateTime;

public class AcaoCliente {

    public enum Acao { BLOQUEAR, DENUNCIAR, IGNORAR }

    private int acaoId;
    private int alertaId;
    private int clienteId;
    private Acao acao;
    private LocalDateTime dataAcao;

    public AcaoCliente() {}

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

    public Acao getAcao() {
        return acao;
    }

    public void setAcao(Acao acao) {
        this.acao = acao;
    }

    public LocalDateTime getDataAcao() {
        return dataAcao;
    }

    public void setDataAcao(LocalDateTime dataAcao) {
        this.dataAcao = dataAcao;
    }

    @Override
    public String toString() {
        return "AcaoCliente{" +
                "acaoId=" + acaoId +
                ", alertaId=" + alertaId +
                ", clienteId=" + clienteId +
                ", acao=" + acao +
                ", dataAcao=" + dataAcao +
                '}';
    }
}
