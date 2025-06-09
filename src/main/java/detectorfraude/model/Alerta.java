package detectorfraude.model;

import java.time.LocalDateTime;

public class Alerta {

    private int alertaId;
    private int debitoId;
    private LocalDateTime dataAlerta;
    private String mensagem;
    private StatusAlerta status;

    // Dados auxiliares (não salvos no banco, mas usados para lógica interna)
    private Cliente cliente;
    private DebitoAutomatico debitoAutomatico;

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

    public StatusAlerta getStatus() {
        return status;
    }

    public void setStatus(StatusAlerta status) {
        this.status = status;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public DebitoAutomatico getDebitoAutomatico() {
        return debitoAutomatico;
    }

    public void setDebitoAutomatico(DebitoAutomatico debitoAutomatico) {
        this.debitoAutomatico = debitoAutomatico;
    }

    @Override
    public String toString() {
        return "Alerta{" +
                "alertaId=" + alertaId +
                ", debitoId=" + debitoId +
                ", dataAlerta=" + dataAlerta +
                ", mensagem='" + mensagem + '\'' +
                ", status=" + status +
                ", cliente=" + (cliente != null ? cliente.getNome() : "null") +
                ", debito=" + (debitoAutomatico != null ? debitoAutomatico.getValor() : "null") +
                '}';
    }
}
