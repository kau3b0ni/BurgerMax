
package br.burgermax.controller;

import br.burgermax.model.Pedido;
import br.burgermax.util.EntityManagerUtil;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;

@ManagedBean(name="pedidoControllerMB")
@SessionScoped
public class PedidoController {
    private Pedido pedido;
    private List<Pedido> listagemPedido;

    public PedidoController(Pedido pedido, List<Pedido> listagemPedido) {
        this.pedido = pedido;
        this.listagemPedido = listagemPedido;
    }

    public PedidoController() {
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public List<Pedido> getListagemPedido() {
        return listagemPedido;
    }

    public void setListagemPedido(List<Pedido> listagemPedido) {
        this.listagemPedido = listagemPedido;
    }
    
    public String cadastrarPedido() {
        String navegacao = "produtoListagem?faces-redirect=true";
        FacesContext contexto = FacesContext.getCurrentInstance();
        FacesMessage mensagem;
        EntityManager em = EntityManagerUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            this.pedido = em.merge(pedido);
            em.getTransaction().commit();
            mensagem = new FacesMessage(
                            FacesMessage.SEVERITY_INFO,
                            "Cadastro/Atualização Realizado com Sucesso!",
                            "Pedido: " + this.pedido.getId() + 
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
                        "Erro.", "Pedido: " + this.pedido.getId() +
                                "Não Cadastrado/Atualizado!");
        }finally {
            em.close();
        }
        return navegacao;
    }
    
    public String prepararCadastro(Pedido pedido) {
        String navegacao = "pedidoCadastro?faces-redirect=true";
        this.pedido = pedido;
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
}
