package br.burgermax.controller;

import br.burgermax.model.Conta;
import br.burgermax.util.EntityManagerUtil;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;

public class ContaController {
    
    private Conta conta;
    private List<Conta> listagemConta;

    public ContaController() {
    }

    public ContaController(Conta conta, List<Conta> listagemConta) {
        this.conta = conta;
        this.listagemConta = listagemConta;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public List<Conta> getListagemConta() {
        return listagemConta;
    }

    public void setListagemConta(List<Conta> listagemConta) {
        this.listagemConta = listagemConta;
    }

    public String cadastrarConta() {
        String navegacao = "contaListagem?faces-redirect=true";
        FacesContext contexto = FacesContext.getCurrentInstance();
        FacesMessage mensagem;
        EntityManager em = EntityManagerUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            this.conta = em.merge(conta);
            em.getTransaction().commit();
            mensagem = new FacesMessage(
                        FacesMessage.SEVERITY_INFO,
                        "Cadastro/Atualização Realizado com Sucesso!",
                        "Conta: " + this.conta.getNome() +
                                "Cadastrado com Sucesso");
            limparSession();
        } catch (Exception e) {
            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }
            em.getTransaction().rollback();
            navegacao = "contaListagem?faces-redirect=true";
            mensagem = new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        "Erro.", "Conta " + this.conta.getNome() + 
                                "Não Cadastrada com Sucesso!");
        }finally {
            em.close();
        }
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
