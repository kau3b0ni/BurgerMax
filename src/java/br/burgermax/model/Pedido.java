
package br.burgermax.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;

@MappedSuperclass
public abstract class Pedido{
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
    @Column(name="pedido_tipo")
    private boolean tipo;

    public Pedido(int id, Double valorTotal, Date dataHora, String estado, String formaPagamento, boolean estadoPagamento, boolean tipo) {
        this.id = id;
        this.valorTotal = valorTotal;
        this.dataHora = dataHora;
        this.estado = estado;
        this.formaPagamento = formaPagamento;
        this.estadoPagamento = estadoPagamento;
        this.tipo = tipo;
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

    public boolean isTipo() {
        return tipo;
    }

    public void setTipo(boolean tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Pedido{" + "id=" + id + ", valorTotal=" + valorTotal + ", dataHora=" + dataHora + ", estado=" + estado + ", formaPagamento=" + formaPagamento + ", estadoPagamento=" + estadoPagamento + ", tipo=" + tipo + '}';
    }
    
    

   
    
    
}
