package detectorfraude.view;

import detectorfraude.model.Cliente;

public class TelaRoute extends javax.swing.JFrame {

    private Cliente cliente;

    public TelaRoute(Cliente cliente) {
        this.cliente = cliente;
        initComponents();
        setTitle("Sistema de Débito Automático Fraudulento");
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        btnExtrato = new javax.swing.JButton();
        btnConsulta = new javax.swing.JButton();
        btnHistorico = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setBackground(new java.awt.Color(204, 0, 0));
        jLabel1.setFont(new java.awt.Font("Malgun Gothic", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Navegação");
        jLabel1.setOpaque(true);

        btnExtrato.setBackground(new java.awt.Color(204, 0, 0));
        btnExtrato.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnExtrato.setForeground(new java.awt.Color(255, 255, 255));
        btnExtrato.setText("Extrato");
        btnExtrato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExtratoActionPerformed(evt);
            }
        });

        btnConsulta.setBackground(new java.awt.Color(204, 0, 0));
        btnConsulta.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnConsulta.setForeground(new java.awt.Color(255, 255, 255));
        btnConsulta.setText("Consulta");
        btnConsulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultaActionPerformed(evt);
            }
        });

        btnHistorico.setBackground(new java.awt.Color(204, 0, 0));
        btnHistorico.setFont(new java.awt.Font("Malgun Gothic", 1, 12)); // NOI18N
        btnHistorico.setForeground(new java.awt.Color(255, 255, 255));
        btnHistorico.setText("Histórico");
        btnHistorico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHistoricoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(btnExtrato)
                .addGap(143, 143, 143)
                .addComponent(btnConsulta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 156, Short.MAX_VALUE)
                .addComponent(btnHistorico)
                .addGap(46, 46, 46))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnExtrato)
                    .addComponent(btnConsulta)
                    .addComponent(btnHistorico))
                .addContainerGap(334, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnExtratoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExtratoActionPerformed
        TelaExtrato tela = new TelaExtrato(this, cliente);
        tela.setVisible(true);
    }//GEN-LAST:event_btnExtratoActionPerformed

    private void btnHistoricoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHistoricoActionPerformed
        TelaConsultaExtrato tela = new TelaConsultaExtrato(this, cliente);
        tela.setVisible(true);
    }//GEN-LAST:event_btnHistoricoActionPerformed

    private void btnConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaActionPerformed
        TelaConsultaDocumento tela = new TelaConsultaDocumento(this);
        tela.setVisible(true);
    }//GEN-LAST:event_btnConsultaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConsulta;
    private javax.swing.JButton btnExtrato;
    private javax.swing.JButton btnHistorico;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
