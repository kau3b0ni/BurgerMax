package br.burgermax.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="jsf_mesa")
public class Mesa implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="mesa_num")
    private int numero;
    @Column(name="mesa_localiza")
    private String localizacao;

    public Mesa() {
    }

    public Mesa(int numero, String localizacao) {
        this.numero = numero;
        this.localizacao = localizacao;
    }

    public int getNumero() {
        return numero;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    @Override
    public String toString() {
        return "Mesa{" + "numero=" + numero + ", localizacao=" + localizacao + '}';
    }
    
    
    
}
