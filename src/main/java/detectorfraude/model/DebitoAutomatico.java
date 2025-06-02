package detectorfraude.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DebitoAutomatico {

    private int id;
    private int idCliente;
    private int idEmpresa;
    private BigDecimal valor;
    private LocalDate dataDebito;
    private boolean recorrente;

    // Construtor padrão
    public DebitoAutomatico() {
    }

    // Construtor completo
    public DebitoAutomatico(int id, int idCliente, int idEmpresa, BigDecimal valor, LocalDate dataDebito, boolean recorrente) {
        this.id = id;
        this.idCliente = idCliente;
        this.idEmpresa = idEmpresa;
        this.valor = valor;
        this.dataDebito = dataDebito;
        this.recorrente = recorrente;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getDataDebito() {
        return dataDebito;
    }

    public void setDataDebito(LocalDate dataDebito) {
        this.dataDebito = dataDebito;
    }

    public boolean isRecorrente() {
        return recorrente;
    }

    public void setRecorrente(boolean recorrente) {
        this.recorrente = recorrente;
    }

    // toString
    @Override
    public String toString() {
        return "DebitoAutomatico{"
                + "id=" + id
                + ", idCliente=" + idCliente
                + ", idEmpresa=" + idEmpresa
                + ", valor=" + valor
                + ", dataDebito=" + dataDebito
                + ", recorrente=" + recorrente
                + '}';
    }
}
