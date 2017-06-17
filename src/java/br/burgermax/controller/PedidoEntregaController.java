
package br.burgermax.controller;

import br.burgermax.model.Cliente;
import br.burgermax.model.Pedido;
import br.burgermax.model.PedidoEntrega;
import br.burgermax.util.EntityManagerUtil;
import java.util.ArrayList;
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
public class PedidoEntregaController {
    
    private PedidoEntrega pedidoEntrega;
    private List<PedidoEntrega> listagemPedidoEntrega;
    private List<Cliente> listagemClientes;

    public PedidoEntregaController(){
        this.pedidoEntrega = new PedidoEntrega();
        this.listagemPedidoEntrega = new ArrayList<PedidoEntrega>();
        this.listagemClientes = new ArrayList<Cliente>();
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
        String navegacao = "pedidosEntrega?faces-redirect=true";
        FacesContext contexto = FacesContext.getCurrentInstance();
        FacesMessage mensagem;
        EntityManager em = EntityManagerUtil.getEntityManager();
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
            navegacao = "pedidosEntrega?faces-redirect=true";
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
        String navegacao = "pedidoCadastro?faces-redirect=true";
        this.pedidoEntrega = pedidoEntrega;
        return navegacao;
    }
    
    public String limparSession() {
        String navegacao = "index";
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
    
    
}
