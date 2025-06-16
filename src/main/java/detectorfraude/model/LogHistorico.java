package detectorfraude.model;

import java.time.LocalDateTime;

public class LogHistorico {

    private int logId;
    private int clienteId;
    private String descricaoEvento;
    private LocalDateTime dataEvento;
    private StatusAcao statusAcao;

    public LogHistorico() {
    }

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public String getDescricaoEvento() {
        return descricaoEvento;
    }

    public void setDescricaoEvento(String descricaoEvento) {
        this.descricaoEvento = descricaoEvento;
    }

    public LocalDateTime getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(LocalDateTime dataEvento) {
        this.dataEvento = dataEvento;
    }
    
    public StatusAcao getStatusAcao() {
        return statusAcao;
    }

    public void setStatusAcao(StatusAcao statusAcao) {
        this.statusAcao = statusAcao;
    }

    @Override
    public String toString() {
        return "LogHistorico{"
                + "logId=" + logId
                + ", clienteId=" + clienteId
                + ", descricaoEvento='" + descricaoEvento + '\''
                + ", dataEvento=" + dataEvento
                + ", statusAcao=" + statusAcao
                + '}';
    }
}
