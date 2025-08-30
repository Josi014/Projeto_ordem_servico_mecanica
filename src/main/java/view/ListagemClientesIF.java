/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package view;

import control.PersistenciaJPA;
import model.Cliente;
import model.OrdemServico;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Comparator;
import java.util.List;

public class ListagemClientesIF extends javax.swing.JInternalFrame {

    private PersistenciaJPA jpa;
    private final JDesktopPane desktop;
    private final ListagemOrdemServicoIF telaListagem;
    private TableRowSorter<DefaultTableModel> sorter;

    public ListagemClientesIF(JDesktopPane desktop, ListagemOrdemServicoIF telaListagem) {
        super("Listagem de Clientes", false, true, false, false);
        this.desktop = desktop;
        this.telaListagem = telaListagem;
        initComponents();
        setSize(500, 600);
        jpa = new PersistenciaJPA();
        configurarTabela();
        ligarFiltro();
        iconePadrao();
        atualizarTabela();
        tiraMovimentacaoTela();
        setVisible(true);
    }

    private void configurarTabela() {
        String[] colunas = {"Código", "Nome", "Telefone"};

        DefaultTableModel model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        listaClientes.setModel(model);

        listaClientes.getTableHeader().setReorderingAllowed(false);
        listaClientes.setRowSorter(null);

        List<Cliente> clientes = jpa.getClientes();
        clientes.sort(Comparator.comparing(Cliente::getId));

        for (Cliente c : clientes) {
            model.addRow(new Object[]{
                c.getId(), c.getNome(), c.getTelefone()
            });
        }
        DefaultTableCellRenderer zebra = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setBackground(isSelected ? new Color(30, 120, 190) : row % 2 == 0 ? Color.WHITE : new Color(230, 240, 255));
                setForeground(isSelected ? Color.WHITE : Color.BLACK);
                setHorizontalAlignment(SwingConstants.LEFT);
                return this;
            }
        };

        DefaultTableCellRenderer centerZebra = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component comp = zebra.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                ((DefaultTableCellRenderer) comp).setHorizontalAlignment(SwingConstants.CENTER);
                return comp;
            }
        };

        listaClientes.setDefaultRenderer(Object.class, zebra);
        listaClientes.getColumnModel().getColumn(0).setCellRenderer(centerZebra);
        listaClientes.setShowGrid(true);
        listaClientes.setGridColor(new Color(150, 180, 240));
    }

    private void ligarFiltro() {
        txtPesquisar.getDocument().addDocumentListener(new DocumentListener() {
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
    }

    private void filtrarTabelaPorNome() {
        String texto = txtPesquisar.getText().toLowerCase();
        DefaultTableModel modelo = (DefaultTableModel) listaClientes.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);
        listaClientes.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto, 1));
    }

    public void atualizarTabela() {
        List<Cliente> clientes = jpa.getClientes();
        DefaultTableModel model = (DefaultTableModel) listaClientes.getModel();
        model.setRowCount(0);
        for (Cliente c : clientes) {
            model.addRow(new Object[]{c.getId(), c.getNome(), c.getTelefone()});
        }
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

    private void iconePadrao() {
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/imagens/icone_ordem_servico_mecanica.png"));
        Image image = originalIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        setFrameIcon(new ImageIcon(image));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnEditar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnAdicionar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        listaClientes = new javax.swing.JTable();
        CriarOrdemServico = new javax.swing.JButton();
        txtPesquisar = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(500, 600));
        setMinimumSize(new java.awt.Dimension(500, 600));
        setPreferredSize(new java.awt.Dimension(500, 600));

        btnEditar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

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

        listaClientes.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(listaClientes);

        CriarOrdemServico.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        CriarOrdemServico.setText("Criar Ordem de Serviço");
        CriarOrdemServico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CriarOrdemServicoActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Pesquisar:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addComponent(btnAdicionar)
                        .addGap(56, 56, 56)
                        .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(CriarOrdemServico, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(57, 57, 57))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(CriarOrdemServico, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(122, Short.MAX_VALUE))
        );

        setBounds(0, 0, 500, 600);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnAdicionarActionPerformed
        CadastrarClientesIF cadastrarClientesIF = new CadastrarClientesIF(desktop, this);
        desktop.add(cadastrarClientesIF);
        cadastrarClientesIF.setVisible(true);
        cadastrarClientesIF.toFront();
        atualizarTabela();
    }// GEN-LAST:event_btnAdicionarActionPerformed

    private void CriarOrdemServicoActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_CriarOrdemServicoActionPerformed
        int linha = listaClientes.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente primeiro");
            return;
        }

        Long clienteId = (Long) listaClientes.getModel().getValueAt(linha, 0);
        Cliente c = jpa.buscarClientePorId(clienteId);

        CadastroOrdemServicoIF ordIF = new CadastroOrdemServicoIF(desktop, telaListagem, c);
        desktop.add(ordIF);
        ordIF.toFront();
        ordIF.setVisible(true);
    }// GEN-LAST:event_CriarOrdemServicoActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnEditarActionPerformed
        int linha = listaClientes.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(null, "Selecione um cliente para editar");
        } else {
            int linhaModelo = listaClientes.convertRowIndexToModel(linha);
            Long clienteId = (Long) listaClientes.getModel().getValueAt(linhaModelo, 0);
            Cliente c = jpa.buscarClientePorId(clienteId);

            CadastrarClientesIF telaCadastro = new CadastrarClientesIF(desktop, this, c);
            desktop.add(telaCadastro);
            telaCadastro.toFront();
            telaCadastro.setVisible(true);

            atualizarTabela();
        }
    }// GEN-LAST:event_btnEditarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnExcluirActionPerformed
        int linhaSelecionada = listaClientes.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(null, "Selecione um cliente para remover");
        } else {
            Long idCliente = (Long) listaClientes.getValueAt(linhaSelecionada, 0);
            String nomeCliente = (String) listaClientes.getValueAt(linhaSelecionada, 1);

            int opDel = JOptionPane.showConfirmDialog(
                    rootPane,
                    "Tem certeza que deseja remover o cliente: " + nomeCliente + "?",
                    "Confirmação",
                    JOptionPane.YES_NO_OPTION);

            if (opDel == JOptionPane.YES_OPTION) {
                if (!jpa.conexaoAberta()) {
                    jpa = new PersistenciaJPA();
                }
                try {
                    Cliente cliente = (Cliente) jpa.find(Cliente.class, idCliente);
                    if (cliente != null) {

                        List<OrdemServico> ordens = jpa.buscarOrdensPorCliente(cliente);
                        for (OrdemServico os : ordens) {
                            os.setCliente(null);
                            os.setClienteNome(cliente.getNome());
                            jpa.atualizarOrdemServico(os);
                        }

                        jpa.removerCliente(cliente);

                        JOptionPane.showMessageDialog(null, "Cliente removido com sucesso");

                    } else {
                        JOptionPane.showMessageDialog(null, "Cliente não encontrado no banco de dados.");
                    }
                } catch (Exception ex) {
                    System.err.println("ERRO AO REMOVER CLIENTE: " + ex);
                    JOptionPane.showMessageDialog(null, "Erro ao remover cliente.");
                }
                atualizarTabela();
            }
        }
    }// GEN-LAST:event_btnExcluirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CriarOrdemServico;
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable listaClientes;
    private javax.swing.JTextField txtPesquisar;
    // End of variables declaration//GEN-END:variables
}
