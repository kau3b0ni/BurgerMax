
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
@Table(name="jsf_pedido")
public class Pedido implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="pedido_id")
    private int id;
    @Column(name="pedido_valor_total")
    private Double valorTotal;
    @Column(name="pedido_data_hora")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataHora;
    @Column(name="pedido_estado")
    private String estado;
    @Column(name="pedido_forma_pagamento")
    private String formaPagamento;
    @Column(name="pedido_estado_pagamento")
    private boolean estadoPagamento;

    public Pedido(int id, Double valorTotal, Date dataHora, String estado, String formaPagamento, boolean estadoPagamento) {
        this.id = id;
        this.valorTotal = valorTotal;
        this.dataHora = dataHora;
        this.estado = estado;
        this.formaPagamento = formaPagamento;
        this.estadoPagamento = estadoPagamento;
    }

    public Pedido() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public boolean isEstadoPagamento() {
        return estadoPagamento;
    }

    public void setEstadoPagamento(boolean estadoPagamento) {
        this.estadoPagamento = estadoPagamento;
    }

    @Override
    public String toString() {
        return "Pedido{" + "id=" + id + ", valorTotal=" + valorTotal + ", dataHora=" + dataHora + ", estado=" + estado + ", formaPagamento=" + formaPagamento + ", estadoPagamento=" + estadoPagamento + '}';
    }
    
    
    
}
