package br.burgermax.controller;

import br.burgermax.model.Mesa;
import br.burgermax.util.EntityManagerUtil;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "mesaControllerMB")
@SessionScoped
public class MesaController {
    
    private Mesa mesa;
    private List<Mesa> listagemMesa;
    
    public MesaController() {
        this.mesa = new Mesa();
    }
    
    public Mesa getMesa() {
        return mesa;
    }
    
    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }
    
    public List<Mesa> getListagemMesa() {
        return listagemMesa;
    }
    
    public void setListagemMesa(List<Mesa> listagemMesa) {
        this.listagemMesa = listagemMesa;
    }
    
    public String cadastrarMesa() {
        String navegacao = "mesaListagem?faces-redirect=true";
        FacesContext contexto = FacesContext.getCurrentInstance();
        FacesMessage mensagem;
        EntityManager em = EntityManagerUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            this.mesa = em.merge(mesa);
            em.getTransaction().commit();
            mensagem = new FacesMessage(
                        FacesMessage.SEVERITY_INFO,
                        "Cadastro/Atualização Efetuada",
                        "Mesa: " + this.mesa.getNumero() +
                                " Cadastro com Sucesso!");
            limparSession();
        } catch (Exception e) {
            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }
            em.getTransaction().rollback();
            navegacao = "mesaListagem?faces-redirect=true";
            mensagem = new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        "Erro.", "Mesa " + this.mesa.getNumero() +
                                "Não Cadastrado/Atualizado!");
        }finally {
            em.close();
        }
        return navegacao;
    }
    
    public String prepararCadastro(Mesa mesa) {
        String navegacao = "mesaCadastro?faces-redirect=true";
        this.mesa = mesa;
        return navegacao;
    }
    
    public String excluiMesa(Mesa mesa) {
        FacesContext contexto = FacesContext.getCurrentInstance();
        FacesMessage mensagem;
        String navegacao = "listagemMesa";
        EntityManager em = EntityManagerUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Mesa mesa2 = em.merge(mesa);
            em.remove(mesa2);
            em.getTransaction().commit();
            this.mesa = mesa2;
            mensagem = new FacesMessage(
                        FacesMessage.SEVERITY_INFO, "Exclusão Efetuada!",
                        "Mesa: " + this.mesa.getNumero() + "Excluida com Sucesso!");
        } catch (Exception e) {
            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }
            em.getTransaction().rollback();
            mensagem = new FacesMessage(
                        FacesMessage.SEVERITY_ERROR, "Erro.",
                        "Mesa: " + this.mesa.getNumero() + "não excluído!");
        }finally {
            em.close();
        }
        contexto.addMessage(null, mensagem);
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
