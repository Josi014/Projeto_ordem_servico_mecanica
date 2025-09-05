/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package view;

import control.PersistenciaJPA;
import static java.awt.SystemColor.desktop;
import javax.swing.plaf.basic.BasicInternalFrameUI;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import model.OrdemServico;
import model.Situacao;
import util.PDFGenerator;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Josieli
 */
public class ListagemOrdemServicoIF extends javax.swing.JInternalFrame {

    PersistenciaJPA jpa;
    private JDesktopPane desktop;

    public ListagemOrdemServicoIF(JDesktopPane desktop) {
        super("Listagem de Serviços", false, false, false, false);
        this.desktop = desktop;
        initComponents();
        setSize(500, 600);
        setVisible(true);
        jpa = new PersistenciaJPA();

        tiraMovimentacaoTela();
        ligarFiltro();
        formataTabela();
        atualizarTabela();
        iconePadrao();
    }

    public void iconePadrao() {
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/imagens/icone_ordem_servico_mecanica.png"));
        Image image = originalIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        setFrameIcon(new ImageIcon(image));
    }

    private void formataTabela() {

        DefaultTableCellRenderer zebra = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                DefaultTableCellRenderer comp
                        = (DefaultTableCellRenderer) super.getTableCellRendererComponent(
                                table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    comp.setBackground(new Color(30, 120, 190));
                    comp.setForeground(Color.WHITE);
                } else {
                    comp.setForeground(Color.BLACK);
                    comp.setBackground(row % 2 == 0
                            ? Color.WHITE
                            : new Color(230, 240, 255));
                }
                comp.setHorizontalAlignment(SwingConstants.LEFT);
                return comp;
            }
        };
        listaServicos.setDefaultRenderer(Object.class, zebra);
        listaServicos.setDefaultRenderer(Number.class, zebra);

        DefaultTableCellRenderer centerZebra = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component comp = zebra.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
                ((DefaultTableCellRenderer) comp)
                        .setHorizontalAlignment(SwingConstants.CENTER);
                return comp;
            }
        };
        listaServicos.getColumnModel().getColumn(0).setCellRenderer(centerZebra);
        listaServicos.getColumnModel().getColumn(1).setCellRenderer(centerZebra);
        listaServicos.getColumnModel().getColumn(3).setCellRenderer(centerZebra);
        listaServicos.getColumnModel().getColumn(2).setCellRenderer(centerZebra);
        listaServicos.getColumnModel().getColumn(4).setCellRenderer(centerZebra);

        listaServicos.setShowGrid(true);
        listaServicos.setGridColor(new Color(150, 180, 240));

    }

    private void tiraMovimentacaoTela() {
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        JComponent northPane = ui.getNorthPane();

        for (MouseListener ml : northPane.getMouseListeners()) {
            northPane.removeMouseListener(ml);
        }
        for (MouseMotionListener mml : northPane.getMouseMotionListeners()) {
            northPane.removeMouseMotionListener(mml);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox1 = new javax.swing.JComboBox<>();
        btnExcluir = new javax.swing.JButton();
        btnAdicionar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        listaServicos = new javax.swing.JTable();
        btnEditar = new javax.swing.JButton();
        gerarPDF = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtPesquisarPorCodigo = new javax.swing.JTextField();
        txtPesquisarPorNome = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtPesquisarPorPlaca = new javax.swing.JTextField();
        btnDuplicar = new javax.swing.JButton();

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setMaximumSize(new java.awt.Dimension(500, 600));
        setMinimumSize(new java.awt.Dimension(500, 600));
        setPreferredSize(new java.awt.Dimension(500, 600));

        btnExcluir.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnExcluir.setText("Excluir");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        btnAdicionar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnAdicionar.setText("Adicionar");
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });

        DefaultTableModel model = new DefaultTableModel(
            new String[] { "Código", "Data", "Nome", "Placa", "Situação" }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        listaServicos.setModel(model); 
        listaServicos.getTableHeader().setReorderingAllowed(false);
        listaServicos.setRowSorter(null);

        listaServicos.setMaximumSize(new java.awt.Dimension(60, 80));
        jScrollPane2.setViewportView(listaServicos);


        btnEditar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        gerarPDF.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        gerarPDF.setText("Gerar arquivo PDF");
        gerarPDF.addActionListener(evt -> GerarArquivoPDFActionPerformed(evt));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Código");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Nome:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Placa:");

        btnDuplicar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnDuplicar.setText("Duplicar");
        btnDuplicar.setMaximumSize(new java.awt.Dimension(87, 27));
        btnDuplicar.setMinimumSize(new java.awt.Dimension(87, 27));
        btnDuplicar.setPreferredSize(new java.awt.Dimension(87, 27));
        btnDuplicar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDuplicarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(btnDuplicar, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(btnAdicionar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPesquisarPorCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPesquisarPorNome, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPesquisarPorPlaca))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addComponent(gerarPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(96, 96, 96))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtPesquisarPorPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtPesquisarPorNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtPesquisarPorCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDuplicar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addComponent(gerarPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(105, Short.MAX_VALUE))
        );

        setBounds(0, 0, 500, 600);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        CadastroOrdemServicoIF cadastroOrdemServicoIF = new CadastroOrdemServicoIF(desktop, this);
        desktop.add(cadastroOrdemServicoIF);
        cadastroOrdemServicoIF.setVisible(true);
        cadastroOrdemServicoIF.toFront();
        atualizarTabela();
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        int linhaSelecionada = listaServicos.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(null, "Selecione um serviço para remover");
        } else {
            int linhaModelo = listaServicos.convertRowIndexToModel(linhaSelecionada);
            Long idOrdemServico = (Long) listaServicos.getModel().getValueAt(linhaModelo, 0);
            String nomeCliente = (String) listaServicos.getModel().getValueAt(linhaModelo, 2);

            int opDel = JOptionPane.showConfirmDialog(
                    rootPane,
                    "Tem certeza que deseja remover o serviço ID: " + idOrdemServico
                    + " do cliente: " + nomeCliente + "?",
                    "Confirmação",
                    JOptionPane.YES_NO_OPTION
            );

            if (opDel == JOptionPane.YES_OPTION) {
                if (!jpa.conexaoAberta()) {
                    jpa = new PersistenciaJPA();
                }
                try {
                    OrdemServico ordem = (OrdemServico) jpa.find(OrdemServico.class, idOrdemServico);
                    if (ordem != null) {
                        jpa.remover(ordem);
                        JOptionPane.showMessageDialog(null, "Serviço removido com sucesso");
                    } else {
                        JOptionPane.showMessageDialog(null, "Serviço não encontrado no banco de dados.");
                    }
                } catch (Exception ex) {
                    System.err.println("ERRO AO REMOVER SERVIÇO: " + ex);
                    JOptionPane.showMessageDialog(null, "Erro ao remover serviço.");
                }
                atualizarTabela();
            }
        }
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        int linhaSelecionada = listaServicos.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(null, "Selecione um serviço para remover");
        } else {
            int linhaModelo = listaServicos.convertRowIndexToModel(linhaSelecionada);
            Long idOrdemServico = (Long) listaServicos.getModel().getValueAt(linhaModelo, 0);
            String nomeCliente = (String) listaServicos.getModel().getValueAt(linhaModelo, 2);

            OrdemServico s = jpa.buscarOrdemServicoPorId(idOrdemServico);

            CadastroOrdemServicoIF telaCadastro = new CadastroOrdemServicoIF(desktop, this, s);
            desktop.add(telaCadastro);
            telaCadastro.toFront();
            telaCadastro.setVisible(true);

            atualizarTabela();
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnDuplicarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDuplicarActionPerformed
        int linhaSelecionada = listaServicos.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(null, "Selecione um serviço para duplicar");
            return;
        }

        int linhaModelo = listaServicos.convertRowIndexToModel(linhaSelecionada);
        Long idOriginal = (Long) listaServicos.getModel().getValueAt(linhaModelo, 0);

        OrdemServico original = jpa.buscarOrdemServicoPorId(idOriginal);
        if (original == null) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar a ordem original");
            return;
        }

        OrdemServico copia = new OrdemServico();
        copia.setDate_abertura_servico(LocalDate.now());
        copia.setCliente(original.getCliente());
        copia.setClienteNome(original.getClienteNome());
        copia.setPlaca(original.getPlaca());
        copia.setModelo(original.getModelo());
        copia.setCor(original.getCor());
        copia.setDescricao_servico(original.getDescricao_servico());
        copia.setSituacao(Situacao.ABERTO);
        copia.setValor_servico(original.getValor_servico());

        Long proximoId = jpa.buscarProximoIdOrdemServico();
        copia.setId(proximoId);

        CadastroOrdemServicoIF telaCadastro = new CadastroOrdemServicoIF(desktop, this, copia);
        desktop.add(telaCadastro);

        telaCadastro.setVisible(true);
        telaCadastro.toFront();
    }//GEN-LAST:event_btnDuplicarActionPerformed

    private void ligarFiltro() {
        txtPesquisarPorCodigo.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filtrarTabelaPorCodigo();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filtrarTabelaPorCodigo();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filtrarTabelaPorCodigo();
            }
        });
        txtPesquisarPorNome.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filtrarTabelaPorNome();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filtrarTabelaPorNome();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filtrarTabelaPorNome();
            }
        });
        txtPesquisarPorPlaca.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filtrarTabelaPorPlaca();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filtrarTabelaPorPlaca();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filtrarTabelaPorPlaca();
            }
        });
    }

    private void filtrarTabelaPorNome() {
        String texto = txtPesquisarPorNome.getText().toLowerCase();
        DefaultTableModel modelo = (DefaultTableModel) listaServicos.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);
        listaServicos.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto, 2));
    }

    private void filtrarTabelaPorPlaca() {
        String texto = txtPesquisarPorPlaca.getText().toLowerCase();
        DefaultTableModel modelo = (DefaultTableModel) listaServicos.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);
        listaServicos.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto, 3));
    }

    private void filtrarTabelaPorCodigo() {
        String texto = txtPesquisarPorCodigo.getText().trim();
        DefaultTableModel modelo = (DefaultTableModel) listaServicos.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);
        listaServicos.setRowSorter(sorter);

        if (texto.isEmpty()) {
            sorter.setRowFilter(null);
            return;
        }
        try {
            long codigo = Long.parseLong(texto);
            sorter.setRowFilter(
                    RowFilter.numberFilter(
                            RowFilter.ComparisonType.EQUAL,
                            codigo,
                            0
                    )
            );
        } catch (NumberFormatException ex) {
            sorter.setRowFilter(
                    RowFilter.regexFilter("(?i)" + texto, 0)
            );
        }
    }

    private void GerarArquivoPDFActionPerformed(java.awt.event.ActionEvent evt) {
        int linha = listaServicos.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(null, "Selecione um serviço para gerar o PDF");
        } else {
            int linhaModelo = listaServicos.convertRowIndexToModel(linha);
            Long ordemId = (Long) listaServicos.getModel().getValueAt(linhaModelo, 0);
            OrdemServico s = jpa.buscarOrdemServicoPorId(ordemId);

            CadastroOrdemServicoIF servico = new CadastroOrdemServicoIF(desktop, this, s);
            servico.GerarPDF();
        }

    }

    public void atualizarTabela() {
        PersistenciaJPA jpa = new PersistenciaJPA();
        List<OrdemServico> ordemServicos = jpa.getOrdensDeServico();

        DefaultTableModel model = (DefaultTableModel) listaServicos.getModel();

        ordemServicos.sort(Comparator.comparing(OrdemServico::getId));

        listaServicos.setEnabled(false);
        model.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (OrdemServico ordem : ordemServicos) {
            String dataFormatada = ordem.getDate_abertura_servico().format(formatter);

            model.addRow(new Object[]{
                ordem.getId(),
                dataFormatada,
                ordem.getClienteNome(),
                ordem.getPlaca(),
                ordem.getSituacao()
            });
        }
        listaServicos.setEnabled(true);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnDuplicar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton gerarPDF;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable listaServicos;
    private javax.swing.JTextField txtPesquisarPorCodigo;
    private javax.swing.JTextField txtPesquisarPorNome;
    private javax.swing.JTextField txtPesquisarPorPlaca;
    // End of variables declaration//GEN-END:variables
}
