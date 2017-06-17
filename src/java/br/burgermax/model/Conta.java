package br.burgermax.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name="jsf_conta")
public class Conta implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="conta_id")
    private int id;
    @Column(name="conta_data_vencimento")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataVencimento;
    @Column(name="conta_valor")
    private Double valor;
    @Column(name="conta_nome")
    private String nome;
    @Column(name="conta_obs")
    private String observacao;
    @Column(name="conta_estado_pagamento")
    private Boolean estadoPagamento;

    public Conta() {
    }

    public Conta(int id, Date dataVencimento, Double valor, String nome, String observacao, Boolean estadoPagamento) {
        this.id = id;
        this.dataVencimento = dataVencimento;
        this.valor = valor;
        this.nome = nome;
        this.observacao = observacao;
        this.estadoPagamento = estadoPagamento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Boolean getEstadoPagamento() {
        return estadoPagamento;
    }

    public void setEstadoPagamento(Boolean estadoPagamento) {
        this.estadoPagamento = estadoPagamento;
    }

    @Override
    public String toString() {
        return "Conta{" + "id=" + id + ", dataVencimento=" + dataVencimento + ", valor=" + valor + ", nome=" + nome + ", observacao=" + observacao + ", estadoPagamento=" + estadoPagamento + '}';
    }
    
    
}
