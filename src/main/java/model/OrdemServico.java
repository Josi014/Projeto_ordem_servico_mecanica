/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

/**
 *
 * @author Josieli
 */
@Entity
@Table(name = "ordem_servico")
public class OrdemServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ordem_servico_id")
    private Long id;

    @Column(name = "cliente_nome", length = 100, nullable = true)
    private String clienteNome;

    @Column(name = "data_abertura_servico", nullable = false)
    private LocalDate date_abertura_servico;

    @Column(name = "data_fechamento_servico")
    private LocalDate date_fechamento_servico;

    @Column(name = "placa", nullable = false, length = 10)
    private String placa;

    @Column(name = "modelo", length = 50)
    private String modelo;

    @Column(name = "cor", length = 50)
    private String Cor;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "situacao", nullable = false, length = 20)
    private Situacao situacao;

    @Column(name = "descricao_servico", nullable = true, columnDefinition = "TEXT")
    private String descricao_servico;

    @Column(name = "valor_servico", nullable = true)
    private Double valor_servico;


    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "cliente_id", nullable = true,
            foreignKey = @ForeignKey(name = "fk_cliente_ordem"))
    private Cliente cliente;

    public OrdemServico() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate_abertura_servico() {
        return date_abertura_servico;
    }

    public void setDate_abertura_servico(LocalDate date_abertura_servico) {
        this.date_abertura_servico = date_abertura_servico;
    }

    public LocalDate getDate_fechamento_servico() {
        return date_fechamento_servico;
    }

    public void setDate_fechamento_servico(LocalDate date_fechamento_servico) {
        this.date_fechamento_servico = date_fechamento_servico;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }

    public String getDescricao_servico() {
        return descricao_servico;
    }

    public void setDescricao_servico(String descricao) {
        this.descricao_servico = descricao;
    }

    public Double getValor_servico() {
        return valor_servico;
    }

    public void setValor_servico(Double valor_servico) {
        this.valor_servico = valor_servico;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }

    public String getCor() {
        return Cor;
    }

    public void setCor(String Cor) {
        this.Cor = Cor;
    }
    
    

     @PrePersist
    @PreUpdate
    private void atualizarClienteNome() {
        this.clienteNome = (this.cliente != null
                ? this.cliente.getNome()
                : this.clienteNome);
    }

    @Override
    public String toString() {
        return id + "" + date_abertura_servico + "" + placa + "" + situacao + "" + clienteNome;
    }

}
