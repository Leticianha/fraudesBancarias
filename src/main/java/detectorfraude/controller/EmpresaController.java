package detectorfraude.controller;

import detectorfraude.dao.EmpresaDAO;
import detectorfraude.model.Empresa;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class EmpresaController {

    private EmpresaDAO empresaDAO;

    public EmpresaController(Connection connection) {
        this.empresaDAO = new EmpresaDAO(connection);
    }

    public void inserir(Empresa empresa) throws SQLException {
        empresaDAO.inserir(empresa);
    }

    public List<Empresa> listarTodas() throws SQLException {
        return empresaDAO.listarTodas();
    }

    public Empresa buscarPorId(int id) throws SQLException {
        return empresaDAO.buscarPorId(id);
    }

    public void atualizar(Empresa empresa) throws SQLException {
        empresaDAO.atualizar(empresa);
    }

    public void deletarPorId(int id) throws SQLException {
        empresaDAO.deletarPorId(id);
    }
}