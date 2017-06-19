
package br.burgermax.controller;

import br.burgermax.model.Categoria;
import br.burgermax.model.Produto;
import br.burgermax.util.EntityManagerUtil;
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

@ManagedBean(name="produtoControllerMB")
@SessionScoped
public class ProdutoController {
    
    private Produto produto;
    private List<Produto> listagemProdutos;
    private List<Categoria> listagemCategorias;

    public ProdutoController() {
        this.produto = new Produto();
        this.listagemCategorias = new ArrayList<Categoria>();
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

    public List<Categoria> getListagemCategorias() {
        return listagemCategorias;
    }

    public void setListagemCategorias(List<Categoria> listagemCategorias) {
        this.listagemCategorias = listagemCategorias;
    }    

    @Override
    public String toString() {
        return "ProdutoController{" + "produto=" + produto + ", listagemProdutos=" + listagemProdutos + ", listagemCategorias=" + listagemCategorias + '}';
    }
    
    
    
    
    public String cadastrarProduto(){
        String navegacao = "produtoListagem?faces-redirect=true";
        FacesContext contexto = FacesContext.getCurrentInstance();
        FacesMessage mensagem;
        
        EntityManager em = EntityManagerUtil.getEntityManager(); 
        
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
        String navegacao = "produtoCadastro?faces-redirect=true";       
        FacesContext context = FacesContext.getCurrentInstance();
                HttpSession session
                = (HttpSession) context.getExternalContext().getSession(false);
        session.invalidate();
        this.produto = new Produto();
        return navegacao;
        
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
    
     public String prepararCadastro(Produto produto){
        String navegacao="produtoCadastro?faces-redirect=true";
        this.produto=produto;
        return navegacao;
    }
     
    public String novoProduto(){
        Produto novoProduto = new Produto();
        String navegacao="produtoCadastro?faces-redirect=true";        
        return navegacao;
    }
    
     public void carregarCategorias(){
       EntityManager em = EntityManagerUtil.getEntityManager();         
       try{
            TypedQuery<Categoria> query = em.createQuery("Select c from Categoria c", Categoria.class); //Cliente.class? 
            this.listagemCategorias = query.getResultList();
        }catch(Exception e){
            System.out.println("Erro: " + e);
        }finally{
            em.close();
        }
            
    }
    
     
      public String excluirProduto(Produto pro){
        String navegacao = "produtoListagem?faces-redirect=true";
        
        FacesContext contexto = FacesContext.getCurrentInstance();
        FacesMessage mensagem;
        
        EntityManager em = EntityManagerUtil.getEntityManager();         
        try{
            em.getTransaction().begin();
            Produto pro2 = em.merge(pro);
            em.remove(pro2);
            em.getTransaction().commit();
            this.produto = pro2;
            mensagem = new FacesMessage(FacesMessage.SEVERITY_INFO,"Exclusão efetuada.",
                    "Produto [ "+this.produto.getNome()+" ] removido.");
            
            
        }catch(Exception e){
            System.out.println("Erro: " + e);
            if(!em.getTransaction().isActive()){
                em.getTransaction().begin();
            }
            em.getTransaction().rollback();
            
            mensagem = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro.",
            "Não foi possível remover o produto.");
            
            
        }finally{
            em.close();
            carregarProdutos();
        }        
        
        contexto.addMessage(null, mensagem);
        return navegacao;        
    }
    
    
     


    
}
