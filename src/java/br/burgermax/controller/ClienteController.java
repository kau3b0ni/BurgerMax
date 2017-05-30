package br.burgermax.controller;

import br.burgermax.model.Cliente;
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

@ManagedBean(name = "clienteControllerMB")
@SessionScoped
public class ClienteController {
    
    private Cliente cliente;
    private List<Cliente> listagemClientes;

    public ClienteController() {
        this.cliente = new Cliente();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Cliente> getListagemClientes() {
        return listagemClientes;
    }

    public void setListagemClientes(List<Cliente> listagemClientes) {
        this.listagemClientes = listagemClientes;
    }

    public String cadastrarCliente(){
        System.out.println(cliente); 
        String navegacao = "clienteListagem?faces-redirect=true";
        
        FacesContext contexto = FacesContext.getCurrentInstance();
        FacesMessage mensagem; 
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("BurgerMaxPU");
        EntityManager em = emf.createEntityManager();        
        try{
            em.getTransaction().begin();
            this.cliente = em.merge(cliente);
            em.getTransaction().commit();            
            mensagem = new FacesMessage(
                    FacesMessage.SEVERITY_INFO,
                    "Cadastro/Atualização efetuada",
                    "Cliente: " + this.cliente.getNome() +
                       " cadastrado/atualizado com sucesso!");
            limparSession();
        }catch(Exception e){
             if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }
            em.getTransaction().rollback();
            navegacao = "clienteListagem?faces-redirect=true";
            mensagem = new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        "Erro.","Cliente " 
                            + this.cliente.getNome() +
                                " não cadastrado/atualizado");
                       
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
    
    public void carregarClientes(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("BurgerMaxPU");
        EntityManager em = emf.createEntityManager();
        try{
            TypedQuery<Cliente> query = em.createQuery("Select c from Cliente c", Cliente.class); //Cliente.class?
            this.listagemClientes = query.getResultList();
        }catch(Exception e){
            System.out.println("Erro: " + e);
        }finally{
            em.close();
        }
            
    }
    
    public String prepararCadastro(Cliente cliente){
        String navegacao="clienteCadastro?faces-redirect=true";
        this.cliente=cliente;
        return navegacao;
    }
    
    public String excluirCliente(Cliente cli){
        String navegacao = "clienteListagem";
        
        
        FacesContext contexto = FacesContext.getCurrentInstance();
        FacesMessage mensagem;
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("BurgerMaxPU");
        EntityManager em = emf.createEntityManager();
        
        try{
            em.getTransaction().begin();
            Cliente cli2 = em.merge(cli);
            em.remove(cli2);
            em.getTransaction().commit();
            this.cliente = cli2;
            mensagem = new FacesMessage(FacesMessage.SEVERITY_INFO,"Exclusão efetuada.",
                    "Cliente [ "+this.cliente.getNome()+" ] removido.");
            
            
        }catch(Exception e){
            System.out.println("Ops, error: " + e);
            if(!em.getTransaction().isActive()){
                em.getTransaction().begin();
            }
            em.getTransaction().rollback();
            
            mensagem = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro.",
            "Não foi possível remover o cliente.");
            
            
        }finally{
            em.close();
            carregarClientes();
        }        
        
        contexto.addMessage(null, mensagem);
        return navegacao;        
    }
    

  
    
         
    
}
