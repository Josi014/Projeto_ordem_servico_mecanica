/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package view;

import control.PersistenciaJPA;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import model.Cliente;
import model.OrdemServico;
import model.Situacao;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;

import com.lowagie.text.Paragraph;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicInternalFrameUI;

import java.time.format.DateTimeParseException;

import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import util.PDFGenerator;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;

/**
 *
 * @author Josieli
 */
public class CadastroOrdemServicoIF extends javax.swing.JInternalFrame {

    private final PersistenciaJPA jpa = new PersistenciaJPA();
    private OrdemServico ordemServico;
    private JDesktopPane desktopPane;
    private ListagemOrdemServicoIF telaListagem;

    public OrdemServico getOrdemServico() {
        return ordemServico;
    }

    public void setOrdemServico(OrdemServico ordemServico) {
        this.ordemServico = ordemServico;
        txtNome.setText(ordemServico.getCliente().getNome());
        txtData.setText("" + ordemServico.getDate_abertura_servico());
        txtCodigo.setText(ordemServico.getCliente().getEmail());
        txtPlaca.setText(ordemServico.getPlaca());
        txtModelo.setText(ordemServico.getModelo());
        txtCor.setText(ordemServico.getCor());
        txtDescricao.setText(ordemServico.getDescricao_servico());
        cmbSituacao.setSelectedItem(ordemServico.getSituacao());
        txtValor.setValue(ordemServico.getValor_servico());
    }

    public void loadStatus() {
        cmbSituacao.removeAllItems();
        for (Situacao status : Situacao.values()) {
            cmbSituacao.addItem(status);
        }
    }

    public void OcultarBotaoPDF() {
        gerarPDF.setVisible(false);
    }

    public void setNomeClienteSelecionado(String nome) {
        txtNome.setText(nome);
    }

    private Cliente clienteSelecionado;

    public CadastroOrdemServicoIF(JDesktopPane desktopPane, ListagemOrdemServicoIF telaListagem, Cliente clienteExistente) {
        super("Cadastro de Ordem de Serviço", false, true, false, false);
        this.desktopPane = desktopPane;
        this.telaListagem = telaListagem;
        this.ordemServico = new OrdemServico();

        initComponents();
        setSize(500, 600);
        setVisible(true);

        loadStatus();
        dataECodigoOrdem();
        tiraMovimentacaoTela();
        configurarCampoValor();
        OcultarBotaoPDF();
        iconePadrao();

        if (clienteExistente != null) {
            this.ordemServico.setCliente(clienteExistente);
            txtNome.setText(clienteExistente.getNome());
            txtNome.setEditable(false);
            Pesquisar.setVisible(false);
        }
    }

    public CadastroOrdemServicoIF(JDesktopPane desktopPane, ListagemOrdemServicoIF telaListagem, OrdemServico ordemServicoExistente) {
        super("Cadastro de Ordem de Serviço", false, true, false, false);
        this.desktopPane = desktopPane;
        this.telaListagem = telaListagem;
        this.ordemServico = ordemServicoExistente;

        initComponents();
        setSize(500, 600);
        setVisible(true);

        loadStatus();
        tiraMovimentacaoTela();
        configurarCampoValor();
        iconePadrao();

        if (ordemServicoExistente != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            txtData.setText(ordemServicoExistente.getDate_abertura_servico().format(formatter));
            txtCodigo.setText(String.valueOf(ordemServicoExistente.getId()));
            txtNome.setText(ordemServicoExistente.getClienteNome());
            txtNome.setEditable(false);
            Pesquisar.setVisible(false);
            txtPlaca.setText(ordemServicoExistente.getPlaca());
            txtModelo.setText(ordemServicoExistente.getModelo());
            txtCor.setText(ordemServicoExistente.getCor());
            cmbSituacao.setSelectedItem(ordemServicoExistente.getSituacao());
            txtDescricao.setText(ordemServicoExistente.getDescricao_servico());
            txtValor.setValue(ordemServicoExistente.getValor_servico());

            if (ordemServicoExistente.getSituacao() == Situacao.FECHADO) {
                cmbSituacao.setEnabled(false);
                txtDescricao.setEditable(false);
                txtValor.setEditable(false);
            }
        }
    }

    public CadastroOrdemServicoIF(JDesktopPane desktopPane, ListagemOrdemServicoIF telaListagem) {
        super("Cadastro de Ordem de Serviço", false, true, false, false);
        this.desktopPane = desktopPane;
        this.telaListagem = telaListagem;
        this.ordemServico = new OrdemServico();

        initComponents();
        setSize(500, 600);
        setVisible(true);

        loadStatus();
        dataECodigoOrdem();
        configurarAutoCompleteCliente();
        txtValor.setValue(0.0);
        tiraMovimentacaoTela();
        configurarCampoValor();
        OcultarBotaoPDF();
        iconePadrao();
    }

    public void iconePadrao() {
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/imagens/icone_ordem_servico_mecanica.png"));
        Image image = originalIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        setFrameIcon(new ImageIcon(image));
    }

    public void dataECodigoOrdem() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        txtData.setText(LocalDate.now().format(formatter));

        Long proximoId = jpa.buscarProximoIdOrdemServico();
        txtCodigo.setText(String.valueOf(proximoId));
    }

    private JPopupMenu popup = new JPopupMenu();
    private Timer debounceTimer;

    public void configurarAutoCompleteCliente() {
        debounceTimer = new Timer(300, (ActionEvent e) -> mostrarSugestoes());
        debounceTimer.setRepeats(false);

        txtNome.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                debounceTimer.restart();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                debounceTimer.restart();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                debounceTimer.restart();
            }
        });
    }

    private void mostrarSugestoes() {
        String texto = txtNome.getText().trim();
        popup.setVisible(false);
        popup.removeAll();

        if (texto.length() < 2) {
            return;
        }

        new SwingWorker<List<Cliente>, Void>() {
            @Override
            protected List<Cliente> doInBackground() {
                return jpa.buscarClientesPorNomeParcial(texto);
            }

            @Override
            protected void done() {
                try {
                    List<Cliente> sugestoes = get();
                    if (sugestoes.isEmpty()) {
                        return;
                    }

                    for (Cliente c : sugestoes) {
                        JMenuItem item = new JMenuItem(c.getNome());
                        item.addActionListener(evt -> {
                            txtNome.setText(c.getNome());
                            popup.setVisible(false);
                            txtNome.requestFocusInWindow();
                        });
                        popup.add(item);
                    }

                    popup.setFocusable(false);
                    popup.setRequestFocusEnabled(false);
                    popup.show(txtNome, 0, txtNome.getHeight());
                    txtNome.requestFocusInWindow();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }.execute();
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

        lblData = new javax.swing.JLabel();
        lblCodigo = new javax.swing.JLabel();
        txtData = new javax.swing.JTextField();
        txtCodigo = new javax.swing.JTextField();
        lblNome = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescricao = new javax.swing.JTextArea();
        lblDescricao = new javax.swing.JLabel();
        lblPlaca = new javax.swing.JLabel();
        txtPlaca = new javax.swing.JTextField();
        lblModelo = new javax.swing.JLabel();
        txtModelo = new javax.swing.JTextField();
        btnLimpar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnFechar = new javax.swing.JButton();
        lblValor = new javax.swing.JLabel();
        txtValor = new javax.swing.JFormattedTextField();
        lblSituacao = new javax.swing.JLabel();
        cmbSituacao = new javax.swing.JComboBox<>();
        gerarPDF = new javax.swing.JButton();
        Pesquisar = new javax.swing.JButton();
        lblCor = new javax.swing.JLabel();
        txtCor = new javax.swing.JTextField();

        setMaximumSize(new java.awt.Dimension(500, 6000));
        setMinimumSize(new java.awt.Dimension(500, 600));
        setPreferredSize(new java.awt.Dimension(500, 600));

        lblData.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblData.setText("Data:");

        lblCodigo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblCodigo.setText("Código:");

        txtCodigo.setEditable(false);

        lblNome.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNome.setText("Nome:");

        txtDescricao.setColumns(20);
        txtDescricao.setRows(5);
        jScrollPane1.setViewportView(txtDescricao);

        lblDescricao.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblDescricao.setText("Descrição:");

        lblPlaca.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblPlaca.setText("Placa:");

        lblModelo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblModelo.setText("Modelo:");

        btnLimpar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnLimpar.setText("Limpar");
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });

        btnSalvar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnFechar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnFechar.setText("Fechar");
        btnFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFecharActionPerformed(evt);
            }
        });

        lblValor.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblValor.setText("Valor: R$");

        lblSituacao.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblSituacao.setText("Situação:");

        gerarPDF.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        gerarPDF.setText("Gerar arquivo PDF");
        gerarPDF.addActionListener(evt -> GerarArquivoPDFActionPerformed(evt));


        Pesquisar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Pesquisar.setText("Pesquisar");
        Pesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PesquisarActionPerformed(evt);
            }
        });

        lblCor.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblCor.setText("Cor:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblValor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblDescricao)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNome)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(lblData, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblPlaca, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                                .addComponent(lblCor, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtData)
                                    .addComponent(txtPlaca, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                                    .addComponent(txtCor))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblCodigo)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblModelo)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblSituacao)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cmbSituacao, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Pesquisar, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE))))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(gerarPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(80, 80, 80)
                            .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(80, 80, 80)
                            .addComponent(btnFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(48, 48, 48))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblData)
                    .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCodigo))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNome)
                            .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Pesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(18, 18, 18)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPlaca)
                    .addComponent(txtPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblModelo)
                    .addComponent(txtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSituacao)
                    .addComponent(cmbSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCor)
                    .addComponent(txtCor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(lblDescricao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblValor)
                    .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(gerarPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(73, Short.MAX_VALUE))
        );

        setBounds(0, 0, 500, 600);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        String nome = txtNome.getText().trim();
        String textoData = txtData.getText().trim();
        String placa = txtPlaca.getText().trim();
        String modelo = txtModelo.getText().trim();
        String cor = txtCor.getText().trim();
        String descricao = txtDescricao.getText().trim();
        Situacao situacao = (Situacao) cmbSituacao.getSelectedItem();

        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha o nome do cliente.");
            return;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate data = LocalDate.parse(textoData, formatter);

            if ((ordemServico == null) && situacao == Situacao.FECHADO) {
                data = LocalDate.now();
                txtData.setText(data.format(formatter));
            } else {
                data = LocalDate.parse(textoData, formatter);
            }

            PersistenciaJPA jpa = new PersistenciaJPA();

            Cliente cliente = jpa.buscarClientePorNome(nome);
            if (cliente == null) {
                cliente = new Cliente();
                cliente.setNome(nome);
                jpa.salvarOuAtualizarCliente(cliente);
            }

            if (ordemServico == null) {
                ordemServico = new OrdemServico();
            }

            Situacao situacaoAnterior = ordemServico.getSituacao();
            if ((situacaoAnterior == Situacao.ABERTO || situacaoAnterior == Situacao.EM_ANDAMENTO)
                    && situacao == Situacao.FECHADO) {
                ordemServico.setDate_fechamento_servico(LocalDate.now());
            }

            ordemServico.setCliente(cliente);
            ordemServico.setClienteNome(nome);
            ordemServico.setDate_abertura_servico(data);
            ordemServico.setPlaca(placa);
            ordemServico.setModelo(modelo);
            ordemServico.setCor(cor);
            ordemServico.setDescricao_servico(descricao);
            ordemServico.setSituacao(situacao);
            ordemServico.setValor_servico(getValorFormatado().doubleValue());

            jpa.salvarOuAtualizarOrdemServico(ordemServico);

            JOptionPane.showMessageDialog(this, "Ordem de serviço salva com sucesso!");

            if (telaListagem != null) {
                telaListagem.atualizarTabela();
            }

            dispose();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao salvar ordem de serviço: " + e.getMessage());
        }

    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        txtNome.setText("");
        txtDescricao.setText("");
        txtModelo.setText("");
        txtPlaca.setText("");
        txtValor.setValue(0.0);
        cmbSituacao.setSelectedIndex(0);
        txtData.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }//GEN-LAST:event_btnLimparActionPerformed

    private void btnFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharActionPerformed
        dispose();
    }//GEN-LAST:event_btnFecharActionPerformed

    private void PesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PesquisarActionPerformed
        PesquisarJF tela = new PesquisarJF(this);
        tela.setLocationRelativeTo(this);
        tela.setVisible(true);
    }//GEN-LAST:event_PesquisarActionPerformed

    private void GerarArquivoPDFActionPerformed(java.awt.event.ActionEvent evt) {
        GerarPDF();
    }

    private File criarArquivoPDFComNomeUnico(File pasta, String nomeBase, String dataFormatada) {
        String nome = nomeBase + "_" + dataFormatada + ".pdf";
        File arquivo = new File(pasta, nome);
        int contador = 1;
        while (arquivo.exists()) {
            String sufixo = "." + contador;
            String nomeSemExt = nomeBase + "_" + dataFormatada;
            arquivo = new File(pasta, nomeSemExt + sufixo + ".pdf");
            contador++;
        }
        return arquivo;
    }

    public void GerarPDF() {
        try {
            OrdemServico ordem = this.ordemServico;

            String userHome = System.getProperty("user.home");
            File pasta = new File(userHome, "OneDrive" + File.separator + "MinhasOrdens");
            if (!pasta.exists()) {
                pasta.mkdirs();
            }

            String nomeCliente = txtNome.getText().trim().replaceAll("\\s+", "_");

            String textoData = txtData.getText().trim();
            DateTimeFormatter formatterIn = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataLocalDate;
            try {
                dataLocalDate = LocalDate.parse(textoData, formatterIn);
            } catch (DateTimeParseException e) {
                dataLocalDate = LocalDate.now();
            }
            String dataFormatadaParaNome = dataLocalDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

            String nomeBase = "OrdemServico_"
                    + ordem.getId() + "_"
                    + nomeCliente + "_"
                    + dataFormatadaParaNome;

            File arquivoPDF = criarArquivoPDFComNomeUnico(pasta, nomeBase, "");

            PDFGenerator.gerarPDFPadrao(arquivoPDF, ordem);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao gerar PDF: " + e.getMessage());
        }
    }

    private void configurarCampoValor() {
        NumberFormat formatoNumero = NumberFormat.getNumberInstance(new Locale("pt", "BR"));
        formatoNumero.setMinimumFractionDigits(2);
        formatoNumero.setMaximumFractionDigits(2);

        NumberFormatter formatter = new NumberFormatter(formatoNumero);
        formatter.setAllowsInvalid(false);
        formatter.setMinimum(0.0);
        formatter.setOverwriteMode(false);

        txtValor.setFormatterFactory(new DefaultFormatterFactory(formatter));
    }

    public BigDecimal getValorFormatado() {
        try {
            Number num = (Number) txtValor.getValue();
            return BigDecimal.valueOf(num.doubleValue());
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Pesquisar;
    private javax.swing.JButton btnFechar;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox<Situacao> cmbSituacao;
    private javax.swing.JButton gerarPDF;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblCor;
    private javax.swing.JLabel lblData;
    private javax.swing.JLabel lblDescricao;
    private javax.swing.JLabel lblModelo;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblPlaca;
    private javax.swing.JLabel lblSituacao;
    private javax.swing.JLabel lblValor;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCor;
    private javax.swing.JTextField txtData;
    private javax.swing.JTextArea txtDescricao;
    private javax.swing.JTextField txtModelo;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtPlaca;
private javax.swing.JFormattedTextField txtValor;
    // End of variables declaration//GEN-END:variables
}
