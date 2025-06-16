package detectorfraude.view;

import detectorfraude.dao.DebitoAutomaticoDAO;
import detectorfraude.dao.EmpresaDAO;
import detectorfraude.model.Cliente;
import detectorfraude.model.DebitoAutomatico;
import detectorfraude.model.Empresa;
import detectorfraude.util.ConexaoMySQL;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.List;
import javax.swing.table.JTableHeader;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

public class TelaExtrato extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(TelaExtrato.class.getName());

    private Cliente cliente;
    private List<DebitoAutomatico> debitos;

    public TelaExtrato(java.awt.Frame parent, Cliente cliente) {
        this.cliente = cliente;
        initComponents();

        JTableHeader header = tabelaExtrato.getTableHeader();
        header.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 16)); // Fonte em negrito
        header.setBackground(new java.awt.Color(241, 229, 235)); // Fundo cinza escuro
        header.setForeground(java.awt.Color.BLACK); // Letras brancas

        tabelaExtrato.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (isSelected) {
                    setBackground(table.getSelectionBackground());
                    setForeground(table.getSelectionForeground());
                } else {
                    if (row % 2 == 0) {
                        setBackground(new java.awt.Color(250, 247, 247));
                        setForeground(java.awt.Color.BLACK);
                    } else {
                        setBackground(new java.awt.Color(241, 229, 235));
                        setForeground(java.awt.Color.BLACK);
                    }
                }
                return this;
            }
        });

        setLocationRelativeTo(parent);
        carregarTabelaExtrato();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaExtrato = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setBackground(new java.awt.Color(204, 0, 0));
        jLabel1.setFont(new java.awt.Font("Malgun Gothic", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Extrato");
        jLabel1.setToolTipText("");
        jLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel1.setOpaque(true);

        tabelaExtrato.setBackground(new java.awt.Color(225, 225, 225));
        tabelaExtrato.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Empresa", "CNPJ ", "Cobrança", "Data", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaExtrato.setGridColor(new java.awt.Color(204, 204, 204));
        tabelaExtrato.setRowHeight(30);
        tabelaExtrato.setSelectionBackground(new java.awt.Color(153, 153, 153));
        tabelaExtrato.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaExtratoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelaExtrato);
        if (tabelaExtrato.getColumnModel().getColumnCount() > 0) {
            tabelaExtrato.getColumnModel().getColumn(0).setResizable(false);
            tabelaExtrato.getColumnModel().getColumn(1).setResizable(false);
            tabelaExtrato.getColumnModel().getColumn(2).setResizable(false);
            tabelaExtrato.getColumnModel().getColumn(3).setResizable(false);
            tabelaExtrato.getColumnModel().getColumn(4).setResizable(false);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 731, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void carregarTabelaExtrato() {
        try (Connection connection = ConexaoMySQL.getConexao()) {
            DebitoAutomaticoDAO debitoDAO = new DebitoAutomaticoDAO(connection);
            EmpresaDAO empresaDAO = new EmpresaDAO(connection);

            debitos = debitoDAO.listarPorCliente(cliente.getClienteId());

            DefaultTableModel model = (DefaultTableModel) tabelaExtrato.getModel();
            model.setRowCount(0); // limpa tabela

            for (DebitoAutomatico debito : debitos) {
                Empresa empresa = empresaDAO.buscarPorId(debito.getEmpresaId());

                model.addRow(new Object[]{
                    empresa.getNome(),
                    empresa.getCnpj(),
                    "R$ " + debito.getValor(),
                    debito.getDataCadastro(),
                    debito.getStatusSuspeita()
                });
            }

            tabelaExtrato.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    int row = tabelaExtrato.rowAtPoint(e.getPoint());
                    int col = tabelaExtrato.columnAtPoint(e.getPoint());

                    if (col == 4) { // coluna Status
                        String status = (String) tabelaExtrato.getValueAt(row, col);
                        if ("Suspeito".equalsIgnoreCase(status)) {
                            DebitoAutomatico debitoSelecionado = debitos.get(row);
                            TelaAlertaCliente telaAlerta = new TelaAlertaCliente(debitoSelecionado.getDebitoId());
                            telaAlerta.setVisible(true);
                        }
                    }
                }
            });

            // Renderer personalizado para a coluna "Status"
            DefaultTableCellRenderer statusRenderer = new DefaultTableCellRenderer() {
                @Override
                public java.awt.Component getTableCellRendererComponent(JTable table, Object value,
                        boolean isSelected, boolean hasFocus,
                        int row, int column) {
                    JLabel label = (JLabel) super.getTableCellRendererComponent(
                            table, value, isSelected, hasFocus, row, column);

                    String status = value.toString().trim().toLowerCase();

                    if (status.equals("suspeito")) {
                        label.setText("Suspeito");
                        label.setForeground(java.awt.Color.RED);
                    } else {
                        label.setText("Aprovado");
                        label.setForeground(new java.awt.Color(0, 128, 0)); // Verde escuro
                    }

                    if (isSelected) {
                        label.setBackground(table.getSelectionBackground());
                    } else {
                        label.setBackground(row % 2 == 0 ? new java.awt.Color(250, 247, 247) : new java.awt.Color(241, 229, 235));
                    }

                    return label;
                }
            };

            // Aplicar apenas na coluna de índice 4 ("Status")
            tabelaExtrato.getColumnModel().getColumn(4).setCellRenderer(statusRenderer);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar extrato: " + e.getMessage());
        }
    }

    private void tabelaExtratoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaExtratoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelaExtratoMouseClicked

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaExtrato.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaExtrato.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaExtrato.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaExtrato.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(() -> {
            Cliente cliente = new Cliente(); // exemplo de cliente (em produção, você passaria o cliente logado)
            cliente.setClienteId(96);
            new TelaExtrato(null, cliente).setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabelaExtrato;
    // End of variables declaration//GEN-END:variables
}
