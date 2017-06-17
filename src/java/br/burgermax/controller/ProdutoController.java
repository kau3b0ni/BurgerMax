
package br.burgermax.controller;

import br.burgermax.model.Cliente;
import br.burgermax.model.Produto;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;

@ManagedBean(name="produtoControllerMB")
@SessionScoped
public class ProdutoController {
    
    private Produto produto;
    private List<Produto> listagemProdutos;

    public ProdutoController() {
        this.produto = new Produto();
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public List<Produto> getListagemProdutos() {
        return listagemProdutos;
    }

    public void setListagemProdutos(List<Produto> listagemProdutos) {
        this.listagemProdutos = listagemProdutos;
    }
    
    public String cadastrarProduto(){
        String navegacao = "produtoListagem?faces-redirect=true";
        FacesContext contexto = FacesContext.getCurrentInstance();
        FacesMessage mensagem;
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("BurgerMaxPU");
        EntityManager em = emf.createEntityManager();
        
        try{
            em.getTransaction().begin();
            this.produto = em.merge(produto);
            em.getTransaction().commit();
            mensagem = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Cadastro/Atualização efetuada.", 
                    "Cliente [ " +this.produto.getDescricao()+ 
                    " ] cadastrado/atualizado.");
            limparSession();
        }catch(Exception e){
            if(!em.getTransaction().isActive()){
                em.getTransaction().begin();                
            }
            em.getTransaction().rollback();
            navegacao="produtoListagem?faces-redirect=true";
            mensagem = new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        "Erro.","Produto " 
                            + this.produto.getDescricao()+
                                " não cadastrado/atualizado");
            
        }finally{
            em.close();            
        }
        return navegacao;
    }

    public String limparSession() {
        String navegacao = "index";       
        FacesContext context = FacesContext.getCurrentInstance();
                HttpSession session
                = (HttpSession) context.getExternalContext().getSession(false);
        session.invalidate();
        return navegacao;
        
    }
    
    public void carregarProdutos(String cat){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("BurgerMaxPU");
        EntityManager em = emf.createEntityManager();
        
       try{
            TypedQuery<Produto> query = em.createQuery("Select p from Produto p where p.categoria='"+cat+"'", Produto.class); //Cliente.class? 
            this.listagemProdutos = query.getResultList();
        }catch(Exception e){
            System.out.println("Erro: " + e);
        }finally{
            em.close();
        }
            
    }
    
     public String prepararCadastro(Produto produto){
        String navegacao="produtoCadastro?faces-redirect=true";
        this.produto=produto;
        return navegacao;
    }
    
    
    
    
     


    
}
