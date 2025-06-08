package detectorfraude.model;

public class Cliente {

    private int clienteId;
    private String nome;
    private String cpf;

    public Cliente() {
    }

    public Cliente(int clienteId, String nome, String cpf) {
        this.clienteId = clienteId;
        this.nome = nome;
        this.cpf = cpf;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return "Cliente{"
                + "clienteId=" + clienteId
                + ", nome='" + nome + '\''
                + ", cpf='" + cpf + '\''
                + '}';
    }
}
