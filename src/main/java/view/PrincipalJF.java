/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import com.formdev.flatlaf.FlatIntelliJLaf;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import control.PersistenciaJPA;
import model.Cliente;
import model.Usuario;
import view.ListagemClientesIF;
import view.ListagemOrdemServicoIF;
import view.CadastroOrdemServicoIF;
import view.CadastrarClientesIF;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Josieli
 */
public class PrincipalJF extends javax.swing.JFrame {

    private PersistenciaJPA persistencia = new PersistenciaJPA();
    private ListagemClientesIF listagemClientesIF;
    private ListagemOrdemServicoIF listagemOrdemServicosIF;

    public PrincipalJF() {
        initComponents();
        configurarFechamento();
        configurarIconeETitulo();
        carregarListaOrdemServico();
    }

 
    private void configurarFechamento() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                persistencia.fecharConexao();
                persistencia.fecharFactory();
                System.out.println("Conexões JPA fechadas com sucesso.");
            }
        });
    }

     private void configurarIconeETitulo() {
        ImageIcon icon = new ImageIcon(getClass().getResource(
                "/imagens/icone_ordem_servico_mecanica.png"));
        setIconImage(icon.getImage());
        setTitle("Sistema de Ordem de Serviço");
        setLocationRelativeTo(null);
    }

    private void carregarListaOrdemServico() {
        listagemOrdemServicosIF = new ListagemOrdemServicoIF(desktopPane);
        desktopPane.add(listagemOrdemServicosIF);
        listagemOrdemServicosIF.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        desktopPane = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        lsClientes = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(43, 46, 52));
        setLocationByPlatform(true);
        setMaximumSize(new java.awt.Dimension(1000, 600));
        setMinimumSize(new java.awt.Dimension(1000, 600));
        setPreferredSize(new java.awt.Dimension(1040, 600));
        setResizable(false);
        setSize(new java.awt.Dimension(1040, 600));

        jLabel2.setBackground(new java.awt.Color(43, 46, 52));
        jLabel2.setMaximumSize(new java.awt.Dimension(540, 600));
        jLabel2.setMinimumSize(new java.awt.Dimension(540, 600));
        jLabel2.setOpaque(true);
        jLabel2.setPreferredSize(new java.awt.Dimension(540, 600));

        desktopPane.setMaximumSize(new java.awt.Dimension(500, 600));
        desktopPane.setPreferredSize(new java.awt.Dimension(500, 600));

        javax.swing.GroupLayout desktopPaneLayout = new javax.swing.GroupLayout(desktopPane);
        desktopPane.setLayout(desktopPaneLayout);
        desktopPaneLayout.setHorizontalGroup(
            desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
        desktopPaneLayout.setVerticalGroup(
            desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );

        jMenuBar1.setMinimumSize(new java.awt.Dimension(112, 30));
        jMenuBar1.setPreferredSize(new java.awt.Dimension(112, 30));

        jMenu1.setText("Cadastrar");
        jMenu1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jMenu1.setMinimumSize(new java.awt.Dimension(80, 30));
        jMenu1.setPreferredSize(new java.awt.Dimension(80, 30));

        lsClientes.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lsClientes.setText("Clientes");
        lsClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lsClientesActionPerformed(evt);
            }
        });
        jMenu1.add(lsClientes);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(808, 808, 808))
            .addGroup(layout.createSequentialGroup()
                .addComponent(desktopPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(desktopPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        getAccessibleContext().setAccessibleDescription("");

        setSize(new java.awt.Dimension(1056, 609));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lsClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lsClientesActionPerformed
        ListagemClientesIF listagemClientesIF = new ListagemClientesIF(desktopPane, listagemOrdemServicosIF);
        desktopPane.add(listagemClientesIF);
        listagemClientesIF.setVisible(true);
        listagemClientesIF.toFront();
    }//GEN-LAST:event_lsClientesActionPerformed

    public class SegurancaUtil {

        public static String hashSenha(String senha) {
            return BCrypt.hashpw(senha, BCrypt.gensalt());
        }

        public static boolean verificarSenha(String senhaDigitada, String hashSalvo) {
            return BCrypt.checkpw(senhaDigitada, hashSalvo);
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem lsClientes;
    // End of variables declaration//GEN-END:variables
}
