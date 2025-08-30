/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import control.PersistenciaJPA;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import view.CadastroOrdemServicoIF;
import model.Cliente;


import javax.swing.*;
import java.awt.*;
/**
 *
 * @author Usuário
 */
public class PesquisarJF extends javax.swing.JFrame {

    private CadastroOrdemServicoIF parent;
    PersistenciaJPA jpa;

    public void loadClientes() {
        String[] colunas = {"Código", "Nome", "Selecionar"};
        DefaultTableModel model = new DefaultTableModel(null, colunas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
        };

        for (Cliente c : jpa.getClientes()) {
            model.addRow(new Object[]{
                c.getId(),
                c.getNome(),
                "Selecionar"});
        }

        tabelaPesquisarClientes.setModel(model);

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
        tabelaPesquisarClientes.setDefaultRenderer(Object.class, zebra);
        tabelaPesquisarClientes.setDefaultRenderer(Number.class, zebra);

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
        tabelaPesquisarClientes.getColumnModel().getColumn(0)
                .setCellRenderer(centerZebra);

        tabelaPesquisarClientes.setShowGrid(true);
        tabelaPesquisarClientes.setGridColor(new Color(220, 220, 220));
    }

    private void adicionarBotaoNaTabela() {
        tabelaPesquisarClientes.getColumn("Selecionar").setCellRenderer(new ButtonRenderer());
        tabelaPesquisarClientes.getColumn("Selecionar").setCellEditor(new ButtonEditor(new JCheckBox()));
        int colunaDoBotao = 2;
        tabelaPesquisarClientes.getColumnModel().getColumn(colunaDoBotao).setPreferredWidth(30);
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setText("+");
            setOpaque(true);
            setBackground(new Color(0, 128, 0));
            setForeground(Color.WHITE);
            setFont(new Font("SansSerif", Font.BOLD, 18));
            setBorderPainted(false);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setText("+");
            setBackground(new Color(0, 128, 0));
            setForeground(Color.WHITE);
            setFont(new Font("SansSerif", Font.BOLD, 18));
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {

        private JButton button;
        private int row;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton("+");
            button.setOpaque(true);
            button.setBackground(new Color(0, 128, 0));
            button.setForeground(Color.WHITE);
            button.setFont(new Font("SansSerif", Font.BOLD, 18));
            button.setBorderPainted(false);

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                    Cliente clienteSelecionado = obterClienteSelecionado(row);
                    parent.setNomeClienteSelecionado(clienteSelecionado.getNome());
                    PesquisarJF.this.dispose();
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            this.row = row;
            button.setBackground(new Color(0, 128, 0));
            button.setForeground(Color.WHITE);
            button.setFont(new Font("SansSerif", Font.BOLD, 18));
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return "+";
        }

        @Override
        public boolean stopCellEditing() {
            return super.stopCellEditing();
        }

        private Cliente obterClienteSelecionado(int row) {
            int modelRow = tabelaPesquisarClientes.convertRowIndexToModel(row);
            Long id = (Long) tabelaPesquisarClientes.getModel().getValueAt(modelRow, 0);
            String nome = (String) tabelaPesquisarClientes.getModel().getValueAt(modelRow, 1);
            Cliente c = new Cliente();
            c.setId(id);
            c.setNome(nome);
            return c;
        }
    }

    public PesquisarJF(CadastroOrdemServicoIF parent) {
        initComponents();
        this.parent = parent;
        this.jpa = new PersistenciaJPA();
        loadClientes();
        adicionarBotaoNaTabela();
        ligarFiltro();
        ImageIcon icon = new ImageIcon(getClass().getResource("/imagens/icone_ordem_servico_mecanica.png"));
        this.setIconImage(icon.getImage());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaPesquisarClientes = new javax.swing.JTable();
        jScrollBar1 = new javax.swing.JScrollBar();
        txtPesquisar = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setType(java.awt.Window.Type.POPUP);

        tabelaPesquisarClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tabelaPesquisarClientes);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Pesquisar:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(txtPesquisar))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jScrollBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(0, 0, 0)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ligarFiltro() {
        txtPesquisar.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filtrarTabela();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filtrarTabela();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filtrarTabela();
            }
        });
    }

    private void filtrarTabela() {
        String texto = txtPesquisar.getText().toLowerCase();
        DefaultTableModel modelo = (DefaultTableModel) tabelaPesquisarClientes.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);
        tabelaPesquisarClientes.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto, 1));
    }

    public void atualizarTabela() {
        PersistenciaJPA jpa = new PersistenciaJPA();
        List<Cliente> clientes = jpa.getClientes();

        DefaultTableModel model = (DefaultTableModel) tabelaPesquisarClientes.getModel();
        model.setRowCount(0);
        for (Cliente c : clientes) {
            model.addRow(new Object[]{c.getId(), c.getNome()});
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabelaPesquisarClientes;
    private javax.swing.JTextField txtPesquisar;
    // End of variables declaration//GEN-END:variables
}
