package br.burgermax.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="jsf_pedido_entrega")
public class PedidoEntrega extends Pedido implements Serializable{    
        
    @ManyToOne
    @JoinColumn(referencedColumnName = "cli_id", name = "ped_ent_cli_id")
    private Cliente cliente;

    public PedidoEntrega(Cliente cliente, int id, Double valorTotal, Date dataHora, String estado, String formaPagamento, boolean estadoPagamento, boolean tipo) {
        super(id, valorTotal, dataHora, estado, formaPagamento, estadoPagamento, tipo);
        this.cliente = cliente;
    }

    public PedidoEntrega(Cliente cliente) {
        this.cliente = cliente;
    }

    public PedidoEntrega() {
    }

   
        

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "PedidoEntrega{" + "cliente=" + cliente + '}';
    }
    

    

  
    
    
    
    
    
    
}
