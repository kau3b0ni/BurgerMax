
package br.burgermax.controller;

import br.burgermax.model.Cliente;
import br.burgermax.model.ItemPedido;
import br.burgermax.model.Pedido;
import br.burgermax.model.PedidoEntrega;
import br.burgermax.util.EntityManagerUtil;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;


@ManagedBean(name="pedidoEntregaControllerMB")
@SessionScoped
public class PedidoEntregaController implements Serializable{
    
    private PedidoEntrega pedidoEntrega;
    private List<PedidoEntrega> listagemPedidoEntrega;
    private List<Cliente> listagemClientes;
    private List<ItemPedido> listagemItensPedidos;    
   

    public PedidoEntregaController(){
        this.pedidoEntrega = new PedidoEntrega();
        this.listagemPedidoEntrega = new ArrayList<PedidoEntrega>();
        this.listagemItensPedidos = new ArrayList<ItemPedido>();
        this.listagemClientes = new ArrayList<Cliente>();
    }   

    public List<ItemPedido> getListagemItensPedidos() {
        return listagemItensPedidos;
    }

    public void setListagemItensPedidos(List<ItemPedido> listagemItensPedidos) {
        this.listagemItensPedidos = listagemItensPedidos;
    }

    public PedidoEntrega getPedidoEntrega() {
        return pedidoEntrega;
    }

    public void setPedidoEntrega(PedidoEntrega pedidoEntrega) {
        this.pedidoEntrega = pedidoEntrega;
    }

    public List<PedidoEntrega> getListagemPedidoEntrega() {
        return listagemPedidoEntrega;
    }

    public void setListagemPedidoEntrega(List<PedidoEntrega> listagemPedidoEntrega) {
        this.listagemPedidoEntrega = listagemPedidoEntrega;
    }
      
    public List<Cliente> getListagemClientes() {
        return listagemClientes;
    }

    public void setListagemClientes(List<Cliente> listagemClientes) {
        this.listagemClientes = listagemClientes;
    }   
    
    public String cadastrarPedidoEntrega() {
        String navegacao = "pedidoListagem?faces-redirect=true";
        FacesContext contexto = FacesContext.getCurrentInstance();
        FacesMessage mensagem;
        EntityManager em = EntityManagerUtil.getEntityManager();
        pedidoEntrega.setEstado("Em produção");
        pedidoEntrega.setEstadoPagamento(false);
        pedidoEntrega.setDataHora(Date.from(Instant.now()));
        pedidoEntrega.setTipo(true);        
        try {
            em.getTransaction().begin();
            this.pedidoEntrega = em.merge(pedidoEntrega);
            em.getTransaction().commit();
            mensagem = new FacesMessage(
                            FacesMessage.SEVERITY_INFO,
                            "Cadastro/Atualização Realizado com Sucesso!",
                            "Pedido: " + this.pedidoEntrega.getId() + 
                                    "Cadastrado com Sucesso!");
            limparSession();
        } catch (Exception e) {
            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }
            em.getTransaction().rollback();
            navegacao = "pedidoListagem?faces-redirect=true";
            mensagem = new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        "Erro.", "Pedido: " + this.pedidoEntrega.getId() +
                                "Não Cadastrado/Atualizado!");
        }finally {
            em.close();
        }
        contexto.addMessage(null, mensagem);          
        FacesContext.getCurrentInstance().
                getExternalContext().getFlash().setKeepMessages(true);
        return navegacao;
    }
    
    public void carregarPedidosEntrega(){
        EntityManager em = EntityManagerUtil.getEntityManager();
        try{
            TypedQuery<PedidoEntrega> query
                  = em.createQuery("Select e from PedidoEntrega e", PedidoEntrega.class);
            this.listagemPedidoEntrega = query.getResultList();
        }catch(Exception e){
            System.out.println("Erro: " + e);
        }finally{
            em.close();
        }
    } 
     
     
     
    public String prepararCadastro(PedidoEntrega pedidoEntrega) {
        String navegacao = "pedidoEntregaCadastro?faces-redirect=true";
        this.pedidoEntrega = pedidoEntrega;
        return navegacao;
    }
    
    public String limparSession() {
        String navegacao = "pedidoEntregaCadastro?faces-redirect=true";
        // Contexto da aplicação
        FacesContext context = FacesContext.getCurrentInstance();
        //Verifica a sessão e a "grava" na variável
        HttpSession session
                = (HttpSession) context.getExternalContext().getSession(false);
        // Fecha/destroi a sessão
        session.invalidate();
        return navegacao;
    }
    
    
    public void carregarClientes(){
        EntityManager em = EntityManagerUtil.getEntityManager();
        try{
            TypedQuery<Cliente> query =
                    em.createQuery("Select c from Cliente c order by c.nome", Cliente.class);
            this.listagemClientes = query.getResultList();
        }catch(Exception e){
            System.out.println("Erro: " + e);
        }finally{
            em.close();
        } 
    }
    
    public void carregarItensPedido(){
        
        EntityManager em = EntityManagerUtil.getEntityManager();
        try{
            TypedQuery<ItemPedido> query=
                    em.createQuery("Select i from ItemPedido i", ItemPedido.class);
            this.listagemItensPedidos = query.getResultList();
        }catch(Exception e){
            System.out.println("Erro: " + e);
        }finally{
            em.close();
        }        
    }
   
   public String novoItemPedido(int pedido_id){
        System.out.println(Integer.toString(pedido_id));
        String navegacao="adicionarItem?faces-redirect=true";   
        ItemPedido item = new ItemPedido();        
        return navegacao;
    }
    
    
    
}
