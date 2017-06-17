
package br.burgermax.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="jsf_item_pedido")
public class ItemPedidoEntrega implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="item_id")
    private int id;
    
    @ManyToOne
    @JoinColumn(referencedColumnName = "pedido_id", name = "item_pedido_id")
    private PedidoEntrega pedidoEntrega;
    
    @ManyToOne
    @JoinColumn(referencedColumnName = "pro_id", name = "item_pro_id")
    private Produto produto;
    
    @Column(name="item_valor")
    private double valor;

    public ItemPedidoEntrega(int id, PedidoEntrega pedidoEntrega, Produto produto, double valor) {
        this.id = id;
        this.pedidoEntrega = pedidoEntrega;
        this.produto = produto;
        this.valor = valor;
    }

    public ItemPedidoEntrega() {
        //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PedidoEntrega getPedidoEntrega() {
        return pedidoEntrega;
    }

    public void setPedidoEntrega(PedidoEntrega pedidoEntrega) {
        this.pedidoEntrega = pedidoEntrega;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
    
    
    
    
    
    
    
    
    
    
}
