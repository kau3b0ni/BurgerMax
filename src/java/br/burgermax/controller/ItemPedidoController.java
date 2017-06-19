/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.burgermax.controller;

import br.burgermax.model.ItemPedido;
import br.burgermax.model.Pedido;
import br.burgermax.model.Produto;
import br.burgermax.util.EntityManagerUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;

/**
 *
 * @author kaue
 */
@ManagedBean(name = "itemPedidoControllerMB")
@SessionScoped
public class ItemPedidoController implements Serializable{
    
    private ItemPedido itemPedido;
    private List<Produto> listagemProdutos;
    private List<ItemPedido> listagemItensPedidos;
    private List<Pedido> listagemPedidos; 
   
    public ItemPedidoController() {
        this.itemPedido = new ItemPedido();
        this.listagemProdutos = new ArrayList<Produto>();
        this.listagemItensPedidos= new ArrayList<ItemPedido>(); 
        this.listagemPedidos= new ArrayList<Pedido>();
    }

    public List<Pedido> getListagemPedidos() {
        return listagemPedidos;
    }

    public void setListagemPedidos(List<Pedido> listagemPedidos) {
        this.listagemPedidos = listagemPedidos;
    }    
    

    public List<ItemPedido> getListagemItensPedidos() {
        return listagemItensPedidos;
    }

    public void setListagemItensPedidos(List<ItemPedido> listagemItensPedidos) {
        this.listagemItensPedidos = listagemItensPedidos;
    }    
    
    public ItemPedido getItemPedido() {
        return itemPedido;
    }

    public void setItemPedido(ItemPedido itemPedido) {
        this.itemPedido = itemPedido;
    }

    public List<Produto> getListagemProdutos() {
        return listagemProdutos;
    }

    public void setListagemProdutos(List<Produto> listagemProdutos) {
        this.listagemProdutos = listagemProdutos;
    }

    @Override
    public String toString() {
        return "ItemPedidoController{" + "itemPedido=" + itemPedido + ", listagemProdutos=" + listagemProdutos + ", listagemItensPedidos=" + listagemItensPedidos + '}';
    }

       
    public String cadastrarItemPedido(int pedido_id){
        String navegacao = "pedidoEntregaCadastro?faces-redirect=true"; 
        FacesContext contexto = FacesContext.getCurrentInstance();
        FacesMessage mensagem;
        
        EntityManager em = EntityManagerUtil.getEntityManager();
        itemPedido.setPedido_id(pedido_id);       
        try{
            em.getTransaction().begin();
            this.itemPedido = em.merge(itemPedido);
            em.getTransaction().commit();
            mensagem = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Cadastro/Atualização efetuada.", 
                    "Item [ " +this.itemPedido.getId()+ 
                    " ] cadastrado/atualizado.");
            limparSession();
        }catch(Exception e){
            if(!em.getTransaction().isActive()){
                em.getTransaction().begin();                
            }
            em.getTransaction().rollback();
            navegacao="pedidoEntregaCadastro?faces-redirect=true";
            mensagem = new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        "Erro.","Item " 
                            + this.itemPedido.getId()+
                                " não cadastrado/atualizado");
            
        }finally{
            em.close();            
        }
        return navegacao;
    }

    public String limparSession() {        
        //String navegacao = "cadastroTelefone?faces-redirect=true"; //VAI PRA TELA DO PEDIDO      
        FacesContext conext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) conext.getExternalContext().getSession(false);        
        session.invalidate();        
        this.itemPedido = new ItemPedido();
        return "index";
    }
    
     public String prepararCadastro(ItemPedido itemPedido) {
        String navegacao = "cadastroTelefone?faces-redirect=true"; //VAI PRA TELA DE ADICIONAR ITEM
        this.itemPedido = itemPedido;       
        return navegacao;
    }
    
    public String excluir(ItemPedido item) {
        // Para testes locais
        System.out.println(item);
        
        // "Captura" o contexto corrente da aplicação para exibir mensagem
        FacesContext contexto = FacesContext.getCurrentInstance();
        // Utilizado para criar a mensagem à ser exibida
        FacesMessage mensagem;
        // Para navegação com Mensagens
        String navegacao = "cadastroPedido";
        
        // Para navegação sem Mensagens
//        String navegacao = "confirmacao?faces-redirect=true";

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("exemplo01JSFPU");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            ItemPedido item2 = em.merge(item);
            em.remove(item2);
            em.getTransaction().commit();
            this.itemPedido = item2;

            // Definindo a mensagem de retorno com sucesso
            mensagem = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exclusão efetuada.",
                    "Item: " + this.itemPedido.getId()+ " excluído com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro:" + e);
            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }
            em.getTransaction().rollback();
            
            // Para navegação sem mensagens
           // navegacao = "erro?faces-redirect=true";

            // Definindo a mensagem de retorno com erro
            mensagem = new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro.",
                    "item: " + this.itemPedido.getId()+ "não excluído!");
        } finally {
            em.close();
            carregarItensPedido();
        }
        // Adicionando mensagem no contexto para exibição ao usuário.
        contexto.addMessage(null, mensagem);
        return navegacao;
    }

    private void carregarItensPedido() {
        EntityManager em = EntityManagerUtil.getEntityManager();
        try{
            TypedQuery<ItemPedido> query =
                    em.createQuery("Select i from ItemPedido", ItemPedido.class);
            this.listagemItensPedidos = query.getResultList();
        }catch(Exception e){
            System.out.println("Erro: " + e);
        }finally{
            em.close();
        }        
    }
    
    public void carregarProdutos(){
        EntityManager em = EntityManagerUtil.getEntityManager(); 
        
       try{
            TypedQuery<Produto> query = em.createQuery("Select p from Produto p order by p.categoria", Produto.class); //Cliente.class? 
            this.listagemProdutos = query.getResultList();
        }catch(Exception e){
            System.out.println("Erro: " + e);
        }finally{
            em.close();
        }
            
    }
    
    
}
