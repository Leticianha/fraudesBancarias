package detectorfraude.service;

import detectorfraude.dao.AlertaDAO;
import detectorfraude.dao.LogHistoricoDAO;
import detectorfraude.model.Alerta;
import detectorfraude.model.Cliente;
import detectorfraude.model.DebitoAutomatico;
import detectorfraude.model.LogHistorico;
import detectorfraude.model.StatusAlerta;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class AlertaService {

    private final Connection connection;
    private final AlertaDAO alertaDAO;
    private final LogHistoricoDAO logDAO;

    public AlertaService(Connection connection) {
        this.connection = connection;
        this.alertaDAO = new AlertaDAO(connection);
        this.logDAO = new LogHistoricoDAO(connection);
    }

    public void gerarAlerta(DebitoAutomatico debito, String motivo, Cliente cliente) throws SQLException {
        Alerta alerta = new Alerta();
        alerta.setDebitoId(debito.getDebitoId());
        alerta.setDataAlerta(LocalDateTime.now());
        alerta.setMensagem(motivo);
        alerta.setStatus(StatusAlerta.Pendente);

        // informações auxiliares (não vão para o banco, mas úteis para logs, toString etc)
        alerta.setCliente(cliente);
        alerta.setDebitoAutomatico(debito);

        alertaDAO.inserir(alerta);
        registrarLogAlerta(alerta);
    }

    private void registrarLogAlerta(Alerta alerta) throws SQLException {
        LogHistorico log = new LogHistorico();
        log.setClienteId(alerta.getCliente().getClienteId());
        log.setDescricaoEvento("Alerta gerado: " + alerta.getMensagem());
        log.setDataEvento(LocalDateTime.now());
        logDAO.inserir(log);
    }

    public List<Alerta> buscarPorCliente(Cliente cliente) throws SQLException {
        return alertaDAO.buscarPorClienteId(cliente.getClienteId());
    }
}
