package detectorfraude.view;

import detectorfraude.dao.*;
import detectorfraude.model.*;
import detectorfraude.service.EmailService;
import detectorfraude.util.ConexaoMySQL;

import javax.swing.*;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TelaAlertaCliente extends javax.swing.JFrame {

    private static final Logger logger = Logger.getLogger(TelaAlertaCliente.class.getName());
    private final int debitoId;

    public TelaAlertaCliente(int debitoId) {
        this.debitoId = debitoId;
        initComponents();
        setLocationRelativeTo(null);
        carregarMensagemAlerta();
    }

    private void carregarMensagemAlerta() {
        try (Connection connection = ConexaoMySQL.getConexao()) {
            AlertaDAO alertaDAO = new AlertaDAO(connection);
            Alerta alerta = alertaDAO.buscarPorDebitoId(debitoId); // Certifique-se de ter esse método

            if (alerta != null) {
                int clienteId = alertaDAO.buscarClienteIdPorAlertaId(alerta.getAlertaId());
                ClienteDAO clienteDAO = new ClienteDAO(connection);
                Cliente cliente = clienteDAO.buscarPorId(clienteId);
                alerta.setCliente(cliente);

                txtMensagem.setText(alerta.getMensagem());
            } else {
                txtMensagem.setText("⚠️ Nenhum alerta encontrado para este débito.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar alerta: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnIgnorar = new javax.swing.JButton();
        txtMensagem = new javax.swing.JLabel();
        btnDenunciar1 = new javax.swing.JButton();
        btnBloquear = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnIgnorar.setBackground(new java.awt.Color(0, 138, 25));
        btnIgnorar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnIgnorar.setForeground(new java.awt.Color(255, 255, 255));
        btnIgnorar.setText("Ignorar");
        btnIgnorar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIgnorarActionPerformed(evt);
            }
        });

        txtMensagem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtMensagem.setText("Possível fraude");

        btnDenunciar1.setBackground(new java.awt.Color(112, 47, 138));
        btnDenunciar1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDenunciar1.setForeground(new java.awt.Color(255, 255, 255));
        btnDenunciar1.setText("Denunciar");
        btnDenunciar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDenunciar1ActionPerformed(evt);
            }
        });

        btnBloquear.setBackground(new java.awt.Color(255, 0, 0));
        btnBloquear.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnBloquear.setForeground(new java.awt.Color(255, 255, 255));
        btnBloquear.setText("Bloquear");
        btnBloquear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBloquearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtMensagem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnBloquear)
                        .addGap(50, 50, 50)
                        .addComponent(btnDenunciar1)
                        .addGap(50, 50, 50)
                        .addComponent(btnIgnorar, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(50, 50, 50))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(txtMensagem, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBloquear, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnIgnorar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDenunciar1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnIgnorarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIgnorarActionPerformed
        registrarAcao("Ignorar");
    }//GEN-LAST:event_btnIgnorarActionPerformed

    private void btnDenunciar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDenunciar1ActionPerformed
        registrarAcao("Denunciar");
    }//GEN-LAST:event_btnDenunciar1ActionPerformed

    private void btnBloquearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBloquearActionPerformed
        registrarAcao("Bloquear");
    }//GEN-LAST:event_btnBloquearActionPerformed

    private void registrarAcao(String acao) {
        try (Connection connection = ConexaoMySQL.getConexao()) {
            AlertaDAO alertaDAO = new AlertaDAO(connection);
            AcaoClienteDAO acaoDAO = new AcaoClienteDAO(connection);
            LogHistoricoDAO logDAO = new LogHistoricoDAO(connection);
            ClienteDAO clienteDAO = new ClienteDAO(connection);
            EmpresaDAO empresaDAO = new EmpresaDAO(connection);
            DebitoAutomaticoDAO debitoDAO = new DebitoAutomaticoDAO(connection);

            Alerta alerta = alertaDAO.buscarPorDebitoId(debitoId);

            if (alerta == null) {
                alerta = new Alerta();
                alerta.setDebitoId(debitoId);
                alerta.setStatus(StatusAlerta.Pendente);
                alerta.setMensagem("Descreva aqui o motivo da denúncia...");
                alerta.setDataAlerta(LocalDateTime.now());
                alerta.setAlertaId(alertaDAO.inserir(alerta));
            }

            int clienteId = alertaDAO.buscarClienteIdPorAlertaId(alerta.getAlertaId());
            Cliente cliente = clienteDAO.buscarPorId(clienteId);
            alerta.setCliente(cliente);

            AcaoCliente acaoCliente = new AcaoCliente();
            acaoCliente.setAlertaId(alerta.getAlertaId());
            acaoCliente.setClienteId(clienteId);
            acaoCliente.setAcao(acao);
            acaoCliente.setDataAcao(LocalDateTime.now());
            acaoDAO.inserir(acaoCliente);

            alerta.setStatus(StatusAlerta.Resolvido);
            alertaDAO.atualizarStatus(alerta.getAlertaId(), StatusAlerta.Resolvido);

            LogHistorico log = new LogHistorico();
            log.setClienteId(cliente.getClienteId());
            log.setDescricaoEvento("Cliente selecionou ação: " + acao + " para débito ID " + debitoId);
            log.setDataEvento(LocalDateTime.now());
            logDAO.inserir(log);

            JOptionPane.showMessageDialog(this, "Ação registrada com sucesso: " + acao);
            dispose();

            DebitoAutomatico debito = debitoDAO.buscarPorId(debitoId);
            Empresa empresa = empresaDAO.buscarPorId(debito.getEmpresaId());

            String emailDestino = "leituraamanda9@gmail.com";
            String assunto = "🚨 Denúncia de Débito Automático - ID " + debitoId;
            String mensagem = "O cliente " + cliente.getNome()
                    + " (ID: " + cliente.getClienteId() + ", E-mail: " + cliente.getEmail() + ") "
                    + "denunciou o débito automático suspeito.\n\n"
                    + "🔸 Empresa: " + empresa.getNome() + "\n"
                    + "🔸 CNPJ: " + empresa.getCnpj() + "\n"
                    + "🔸 Situação Cadastral: "
                    + (empresa.getSituacaoCadastral() != null ? empresa.getSituacaoCadastral() : "Não disponível") + "\n"
                    + "🔸 Débito ID: " + debitoId + "\n\n"
                    + "Essa denúncia foi registrada automaticamente pelo sistema.";

            EmailService.enviarEmail(emailDestino, assunto, mensagem);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao registrar ação: " + e.getMessage());
        }
    }

    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(Level.SEVERE, "Erro ao setar look and feel", ex);
        }

        //</editor-fold>
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBloquear;
    private javax.swing.JButton btnDenunciar1;
    private javax.swing.JButton btnIgnorar;
    private javax.swing.JLabel txtMensagem;
    // End of variables declaration//GEN-END:variables
}
