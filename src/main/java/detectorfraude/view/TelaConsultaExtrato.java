package detectorfraude.view;

import detectorfraude.controller.DeteccaoFraudeController;
import detectorfraude.model.Cliente;
import java.sql.Connection;
import java.sql.SQLException;
import detectorfraude.dao.ExtratoDAO;
import java.util.List;
import detectorfraude.model.ExtratoDTO;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import detectorfraude.view.BarraProgressoDuplo;
import java.awt.Color;
import java.math.BigDecimal;

public class TelaConsultaExtrato extends javax.swing.JFrame {

    private Cliente cliente;
    private Connection conn;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private BarraProgressoDuplo barraProgressoDuplo;

    public TelaConsultaExtrato(java.awt.Frame parent, Cliente cliente, Connection _conn) throws SQLException {
        initComponents();
        this.cliente = cliente;
        this.conn = _conn;

        BarraProgressoDuplo barra = new BarraProgressoDuplo();
        barra.setPreferredSize(new Dimension(200, 20));
        painelGrafico.setLayout(new BorderLayout());
        painelGrafico.add(barra, BorderLayout.CENTER);

        // Guarde esta barra numa variável da sua classe para atualizar depois
        this.barraProgressoDuplo = barra;

        // Popula a tabela inicialmente com apenas Denunciado e Bloqueado
        carregarExtrato();

        JTableHeader header = tabelaHistorico.getTableHeader();
        header.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 16));
        header.setBackground(new java.awt.Color(241, 229, 235));
        header.setForeground(java.awt.Color.BLACK);

        tabelaHistorico.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
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

                if (column == 4 && value != null) {
                    String status = value.toString();
                    if (status.equalsIgnoreCase("Denunciado")) {
                        setForeground(new java.awt.Color(112, 47, 138)); // Roxo
                    } else if (status.equalsIgnoreCase("Bloqueado")) {
                        setForeground(new java.awt.Color(255, 0, 0)); // Vermelho
                    }
                }

                return this;
            }
        });
    }

    // Método que carrega só Denunciado e Bloqueado na tabela
    private void carregarExtrato() {
        ExtratoDAO extratoDAO = new ExtratoDAO();
        List<ExtratoDTO> extratos = extratoDAO.listarExtratoPorCliente(cliente.getClienteId(), conn);

        DefaultTableModel model = (DefaultTableModel) tabelaHistorico.getModel();
        model.setRowCount(0);

        int countDenunciados = 0;
        int countBloqueados = 0;

        for (ExtratoDTO extrato : extratos) {
            String status = extrato.getStatusAcao();

            // Considera só Denunciado e Bloqueado
            if (status.equalsIgnoreCase("Denunciado") || status.equalsIgnoreCase("Bloqueado")) {
                if (status.equalsIgnoreCase("Denunciado")) {
                    countDenunciados++;
                } else if (status.equalsIgnoreCase("Bloqueado")) {
                    countBloqueados++;
                }

                model.addRow(new Object[]{
                    extrato.getNomeEmpresa(),
                    extrato.getCnpj(),
                    String.format("R$ %.2f", extrato.getValor()),
                    sdf.format(extrato.getData()),
                    status
                });

                // Agora aqui dentro está tudo certo para usar extrato e status
                DeteccaoFraudeController controller = new DeteccaoFraudeController();
                String resumo;
                resumo = controller.gerarResumoDebito(
                        extrato.getNomeEmpresa(),
                        extrato.getCnpj(),
                        BigDecimal.valueOf(extrato.getValor()),
                        sdf.format(extrato.getData()),
                        status
                );
                System.out.println(resumo);
            }
        }

        lblDenunciados.setText("Débitos denunciados: " + String.format("%02d", countDenunciados));
        lblBloqueados.setText("Débitos bloqueados: " + String.format("%02d", countBloqueados));

        int total = countDenunciados + countBloqueados;
        if (total == 0) {
            total = 1; // evita divisão por zero
        }

        barraProgressoDuplo.setValores(countDenunciados, countBloqueados);
    }

    private void filtrarExtratoPorStatus(String statusFiltro) {
        ExtratoDAO extratoDAO = new ExtratoDAO();
        List<ExtratoDTO> extratos = extratoDAO.listarExtratoPorCliente(cliente.getClienteId(), conn);

        DefaultTableModel model = (DefaultTableModel) tabelaHistorico.getModel();
        model.setRowCount(0);

        int countDenunciados = 0;
        int countBloqueados = 0;

        for (ExtratoDTO extrato : extratos) {
            String status = extrato.getStatusAcao();

            // Só considera Denunciado e Bloqueado, ignora Pendente e Ignorado
            if (!(status.equalsIgnoreCase("Denunciado") || status.equalsIgnoreCase("Bloqueado"))) {
                continue;
            }

            boolean incluir = false;

            if ("Status".equals(statusFiltro)) {
                incluir = true; // mostrar tudo que seja Denunciado ou Bloqueado
            } else if (status.equalsIgnoreCase(statusFiltro)) {
                incluir = true;
            }

            if (incluir) {
                if (status.equalsIgnoreCase("Denunciado")) {
                    countDenunciados++;
                } else if (status.equalsIgnoreCase("Bloqueado")) {
                    countBloqueados++;
                }

                model.addRow(new Object[]{
                    extrato.getNomeEmpresa(),
                    extrato.getCnpj(),
                    String.format("R$ %.2f", extrato.getValor()),
                    sdf.format(extrato.getData()),
                    status
                });

                DeteccaoFraudeController controller = new DeteccaoFraudeController();
                String resumo = controller.gerarResumoDebito(
                        extrato.getNomeEmpresa(),
                        extrato.getCnpj(),
                        BigDecimal.valueOf(extrato.getValor()),
                        sdf.format(extrato.getData()),
                        status
                );

                System.out.println(resumo);

            }
        }

        lblDenunciados.setText("Débitos denunciados: " + countDenunciados);
        lblBloqueados.setText("Débitos bloqueados: " + countBloqueados);
        int total = countDenunciados + countBloqueados;
        if (total == 0) {
            total = 1; // para evitar divisão por zero
        }

        barraProgressoDuplo.setValores(countDenunciados, countBloqueados);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        lblBloqueados = new javax.swing.JLabel();
        lblDenunciados = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaHistorico = new javax.swing.JTable();
        painelGrafico = new javax.swing.JPanel();
        btnPDDF = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setBackground(new java.awt.Color(204, 0, 0));
        jLabel1.setFont(new java.awt.Font("Malgun Gothic", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Relatório de extrato");
        jLabel1.setOpaque(true);

        jComboBox1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Status", "Denunciado", "Bloqueado" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        lblBloqueados.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblBloqueados.setText("Débitos bloqueados");

        lblDenunciados.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblDenunciados.setText("Débitos denunciados");

        tabelaHistorico.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Empresa", "Cnpj", "Cobrança", "Data", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaHistorico.setRowHeight(30);
        jScrollPane1.setViewportView(tabelaHistorico);
        if (tabelaHistorico.getColumnModel().getColumnCount() > 0) {
            tabelaHistorico.getColumnModel().getColumn(0).setResizable(false);
            tabelaHistorico.getColumnModel().getColumn(1).setResizable(false);
            tabelaHistorico.getColumnModel().getColumn(2).setResizable(false);
            tabelaHistorico.getColumnModel().getColumn(3).setResizable(false);
            tabelaHistorico.getColumnModel().getColumn(4).setResizable(false);
        }

        painelGrafico.setBackground(new java.awt.Color(153, 153, 153));

        javax.swing.GroupLayout painelGraficoLayout = new javax.swing.GroupLayout(painelGrafico);
        painelGrafico.setLayout(painelGraficoLayout);
        painelGraficoLayout.setHorizontalGroup(
            painelGraficoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        painelGraficoLayout.setVerticalGroup(
            painelGraficoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        btnPDDF.setText("PDF");
        btnPDDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPDDFActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 731, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblBloqueados)
                                .addGap(90, 90, 90)
                                .addComponent(lblDenunciados)
                                .addGap(90, 90, 90)
                                .addComponent(painelGrafico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnPDDF)
                                .addGap(170, 170, 170)))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPDDF))
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblBloqueados)
                        .addComponent(lblDenunciados))
                    .addComponent(painelGrafico, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        String statusSelecionado = (String) jComboBox1.getSelectedItem();
        filtrarExtratoPorStatus(statusSelecionado);
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void btnPDDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPDDFActionPerformed
        try {
            // Caminho do arquivo
            String filePath = System.getProperty("user.home") + "/Desktop/relatorio_extrato.pdf";

            // Criação do documento PDF
            com.itextpdf.text.Document document = new com.itextpdf.text.Document();
            com.itextpdf.text.pdf.PdfWriter.getInstance(document, new java.io.FileOutputStream(filePath));
            document.open();

            // Título
            document.add(new com.itextpdf.text.Paragraph("Relatório de Débitos - Cliente: " + cliente.getNome()));
            document.add(new com.itextpdf.text.Paragraph(" ")); // linha em branco

            // Criar tabela com 6 colunas (5 atuais + 1 para resumo)
            com.itextpdf.text.pdf.PdfPTable table = new com.itextpdf.text.pdf.PdfPTable(6);

            // Cabeçalhos
            table.addCell("Nome da Empresa");
            table.addCell("CNPJ");
            table.addCell("Valor");
            table.addCell("Data");
            table.addCell("Status");
            table.addCell("Resumo");

            DeteccaoFraudeController controller = new DeteccaoFraudeController();

            // Agora percorre os dados da tabela para preencher as linhas e resumo
            for (int i = 0; i < tabelaHistorico.getRowCount(); i++) {
                String nomeEmpresa = tabelaHistorico.getValueAt(i, 0).toString();
                String cnpj = tabelaHistorico.getValueAt(i, 1).toString();

                // Valor precisa ser convertido de String para double
                String valorStr = tabelaHistorico.getValueAt(i, 2).toString().replace("R$ ", "").replace(",", ".");
                double valor = Double.parseDouble(valorStr);

                String dataStr = tabelaHistorico.getValueAt(i, 3).toString();
                String status = tabelaHistorico.getValueAt(i, 4).toString();

                // Adiciona células normais
                table.addCell(nomeEmpresa);
                table.addCell(cnpj);
                table.addCell(String.format("R$ %.2f", valor));
                table.addCell(dataStr);
                table.addCell(status);

                // Gera resumo chamando o controller e adiciona ao PDF
                String resumo = controller.gerarResumoDebito(
                        nomeEmpresa,
                        cnpj,
                        BigDecimal.valueOf(valor),
                        dataStr,
                        status
                );
                table.addCell(resumo);
            }

            document.add(table);
            document.close();

            javax.swing.JOptionPane.showMessageDialog(this, "PDF gerado com sucesso em: " + filePath);

        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Erro ao gerar PDF: " + e.getMessage());

        }    }//GEN-LAST:event_btnPDDFActionPerformed

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
            java.util.logging.Logger.getLogger(TelaConsultaExtrato.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaConsultaExtrato.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaConsultaExtrato.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaConsultaExtrato.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPDDF;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBloqueados;
    private javax.swing.JLabel lblDenunciados;
    private javax.swing.JPanel painelGrafico;
    private javax.swing.JTable tabelaHistorico;
    // End of variables declaration//GEN-END:variables
}
