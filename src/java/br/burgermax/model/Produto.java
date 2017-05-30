
package br.burgermax.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="jsf_produto")
public class Produto implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="pro_id")    
    private int id;
    @Column(name="pro_descricao")    
    private String descricao;
    @Column(name="pro_valor")    
    private double valor;
    @Column(name="pro_categoria")    
    private String categoria;

    public Produto() {
    }

    public Produto(String descricao, double valor, String categoria) {
        this.descricao = descricao;
        this.valor = valor;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Produto{" + "id=" + id + ", descricao=" + descricao + ", valor=" + valor + ", categoria=" + categoria + '}';
    }
    
    
    
    
}
