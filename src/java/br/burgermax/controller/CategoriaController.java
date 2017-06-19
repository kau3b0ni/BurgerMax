
package br.burgermax.controller;

import br.burgermax.model.Categoria;
import br.burgermax.model.Cliente;
import br.burgermax.util.EntityManagerUtil;
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


@ManagedBean(name = "categoriaControllerMB")
@SessionScoped
public class CategoriaController {
    
    private Categoria categoria;
    private List<Categoria> listagemCategorias;
    
    public CategoriaController() {
        this.categoria = new Categoria();
    }
    
    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<Categoria> getListagemCategorias() {
        return listagemCategorias;
    }

    public void setListagemCategorias(List<Categoria> listagemCategorias) {
        this.listagemCategorias = listagemCategorias;
    }
    
        
    public String cadastrarCategoria(){
         System.out.println(categoria);
         String navegacao = "categoriaListagem?faces-redirect=true";
         FacesContext contexto = FacesContext.getCurrentInstance();
         FacesMessage mensagem;
         
         EntityManager em = EntityManagerUtil.getEntityManager();
         try{
             em.getTransaction().begin();
             this.categoria = em.merge(categoria);
             em.getTransaction().commit();
             mensagem = new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Cadastro/Atualização realizado!",
                            "Categoria: " + this.categoria.getNome() + 
                                    "Cadastrado com sucesso!");
             limparSession();
         }catch(Exception e){
            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }
            em.getTransaction().rollback();
            navegacao = "categoriaListagem?faces-redirect=true";
            mensagem = new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        "Erro.","Categoria " 
                            + this.categoria.getNome() +
                                " não cadastrada/atualizada");
                          
         }finally{
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
    
    public void carregarCategorias(){
        EntityManager em = EntityManagerUtil.getEntityManager(); 
        try{
            TypedQuery<Categoria> query = em.createQuery("Select c from Categoria c", Categoria.class); 
            this.listagemCategorias = query.getResultList();
        }catch(Exception e){
            System.out.println("Erro: " + e);
        }finally{
            em.close();
        }            
    }
    
    public String prepararCadastro(Categoria categoria){
        String navegacao="categoriaCadastro?faces-redirect=true";
        this.categoria=categoria;
        return navegacao;
    }
    
    public String excluirCategoria(Categoria cat){
        String navegacao = "categoriaListagem?faces-redirect=true";
        
        FacesContext contexto = FacesContext.getCurrentInstance();
        FacesMessage mensagem;
        
        EntityManager em = EntityManagerUtil.getEntityManager();         
        try{
            em.getTransaction().begin();
            Categoria cat2 = em.merge(cat);
            em.remove(cat2);
            em.getTransaction().commit();
            this.categoria = cat2;
            mensagem = new FacesMessage(FacesMessage.SEVERITY_INFO,"Exclusão efetuada.",
                    "Categoria [ "+this.categoria.getNome()+" ] removido.");
            
            
        }catch(Exception e){
            System.out.println("Erro: " + e);
            if(!em.getTransaction().isActive()){
                em.getTransaction().begin();
            }
            em.getTransaction().rollback();
            
            mensagem = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro.",
            "Não foi possível remover a categoria.");
            
            
        }finally{
            em.close();
            carregarCategorias();
        }        
        
        contexto.addMessage(null, mensagem);
        return navegacao;        
    }
    
    
    
}
