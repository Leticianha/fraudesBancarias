package detectorfraude.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DebitoAutomatico {

    public enum TipoRecorrencia { MENSAL, SEMANAL, ANUAL, UNICO }
    public enum StatusSuspeita { NORMAL, SUSPEITO, CONFIRMADO_COMO_FRAUDE }
    public enum StatusAtivo { ATIVO, INATIVO }

    private int debitoId;
    private int clienteId;
    private int empresaId;
    private BigDecimal valor;
    private LocalDate dataCadastro;
    private LocalDate dataAgendamento; // ✅ Novo atributo
    private String status;              // ✅ Novo atributo
    private TipoRecorrencia tipoRecorrencia;
    private StatusSuspeita statusSuspeita;
    private StatusAtivo statusAtivo;

    public DebitoAutomatico() {}

    public int getDebitoId() {
        return debitoId;
    }

    public void setDebitoId(int debitoId) {
        this.debitoId = debitoId;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(int empresaId) {
        this.empresaId = empresaId;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public LocalDate getDataAgendamento() {
        return dataAgendamento;
    }

    public void setDataAgendamento(LocalDate dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TipoRecorrencia getTipoRecorrencia() {
        return tipoRecorrencia;
    }

    public void setTipoRecorrencia(TipoRecorrencia tipoRecorrencia) {
        this.tipoRecorrencia = tipoRecorrencia;
    }

    public StatusSuspeita getStatusSuspeita() {
        return statusSuspeita;
    }

    public void setStatusSuspeita(StatusSuspeita statusSuspeita) {
        this.statusSuspeita = statusSuspeita;
    }

    public StatusAtivo getStatusAtivo() {
        return statusAtivo;
    }

    public void setStatusAtivo(StatusAtivo statusAtivo) {
        this.statusAtivo = statusAtivo;
    }

    @Override
    public String toString() {
        return "DebitoAutomatico{" +
                "debitoId=" + debitoId +
                ", clienteId=" + clienteId +
                ", empresaId=" + empresaId +
                ", valor=" + valor +
                ", dataCadastro=" + dataCadastro +
                ", dataAgendamento=" + dataAgendamento +
                ", status='" + status + '\'' +
                ", tipoRecorrencia=" + tipoRecorrencia +
                ", statusSuspeita=" + statusSuspeita +
                ", statusAtivo=" + statusAtivo +
                '}';
    }
}
