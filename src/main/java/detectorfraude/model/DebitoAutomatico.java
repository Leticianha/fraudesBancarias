package detectorfraude.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class DebitoAutomatico {

    private int debitoId;
    private int clienteId;
    private int empresaId;
    private BigDecimal valor;
    private LocalDate dataCadastro;
    private String tipoRecorrencia;
    private String statusSuspeita;
    private String statusAtivo;
    private StatusAcao statusAcao;

    // Getters e Setters
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

    public String getTipoRecorrencia() {
        return tipoRecorrencia;
    }

    public void setTipoRecorrencia(String tipoRecorrencia) {
        this.tipoRecorrencia = tipoRecorrencia;
    }

    public String getStatusSuspeita() {
        return statusSuspeita;
    }

    public void setStatusSuspeita(String statusSuspeita) {
        this.statusSuspeita = statusSuspeita;
    }

    public String getStatusAtivo() {
        return statusAtivo;
    }

    public void setStatusAtivo(String statusAtivo) {
        this.statusAtivo = statusAtivo;
    }

    public StatusAcao getStatusAcao() {
        return statusAcao;
    }

    public void setStatusAcao(StatusAcao statusAcao) {
        this.statusAcao = statusAcao;
    }

    @Override
    public String toString() {
        return "DebitoAutomatico{"
                + "debitoId=" + debitoId
                + ", clienteId=" + clienteId
                + ", empresaId=" + empresaId
                + ", valor=" + valor
                + ", dataCadastro=" + dataCadastro
                + ", tipoRecorrencia='" + tipoRecorrencia + '\''
                + ", statusSuspeita='" + statusSuspeita + '\''
                + ", statusAtivo='" + statusAtivo + '\''
                + ", statusAcao='" + statusAcao + '\''
                + '}';
    }

    public void setData(Date parse) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
