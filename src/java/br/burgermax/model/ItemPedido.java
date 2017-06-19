
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
@Table(name = "jsf_item_pedido")
public class ItemPedido implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "item_id")
    private int id;
    
    @ManyToOne
    @JoinColumn(name = "item_produto")
    private Produto produto;
    
    @Column(name = "item_pedido_id")
    private int pedido_id;
    
    @Column(name = "item_valor")
    private double valor;

    public ItemPedido() {
    }

    public ItemPedido(int id, Produto produto, int pedido_id, double valor) {
        this.id = id;
        this.produto = produto;
        this.pedido_id = pedido_id;
        this.valor = valor;
    }
    
    public int getPedido_id() {
        return pedido_id;
    }

    public void setPedido_id(int pedido_id) {
        this.pedido_id = pedido_id;
    }   

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = produto.getValor();
    }   
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    @Override
    public String toString() {
        return "ItemPedido{" + "id=" + id + ", produto=" + produto + ", pedido_id=" + pedido_id + ", valor=" + valor + '}';
    }

    
       
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ItemPedido other = (ItemPedido) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
    
            
}
