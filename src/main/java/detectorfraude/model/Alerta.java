package detectorfraude.model;

import java.time.LocalDateTime;

public class Alerta {

    private int alertaId;
    private int debitoId;
    private LocalDateTime dataAlerta;
    private String mensagem;
    private String statusAlerta;

    // Getters e Setters
    public int getAlertaId() {
        return alertaId;
    }

    public void setAlertaId(int alertaId) {
        this.alertaId = alertaId;
    }

    public int getDebitoId() {
        return debitoId;
    }

    public void setDebitoId(int debitoId) {
        this.debitoId = debitoId;
    }

    public LocalDateTime getDataAlerta() {
        return dataAlerta;
    }

    public void setDataAlerta(LocalDateTime dataAlerta) {
        this.dataAlerta = dataAlerta;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getStatusAlerta() {
        return statusAlerta;
    }

    public void setStatusAlerta(String statusAlerta) {
        this.statusAlerta = statusAlerta;
    }

    @Override
    public String toString() {
        return "Alerta{"
                + "alertaId=" + alertaId
                + ", debitoId=" + debitoId
                + ", dataAlerta=" + dataAlerta
                + ", mensagem='" + mensagem + '\''
                + ", statusAlerta='" + statusAlerta + '\''
                + '}';
    }
}
