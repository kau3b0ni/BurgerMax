
package br.burgermax.controller;


import br.burgermax.model.ItemPedidoEntrega;
import br.burgermax.model.PedidoEntrega;
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

@ManagedBean(name="itemPedidoEntregaControllerMB")
@SessionScoped
public class ItemPedidoEntregaController {    
      
    private ItemPedidoEntrega itemPedidoEntrega;
    private List<ItemPedidoEntrega> listagemItemPedidoEntrega;
    private List<PedidoEntrega> listagemPedidoEntrega;
    private List<Produto> listagemProdutos;
    
    public ItemPedidoEntregaController() {
        this.itemPedidoEntrega = new ItemPedidoEntrega();
        this.listagemItemPedidoEntrega = new ArrayList<ItemPedidoEntrega>();
        this.listagemPedidoEntrega = new ArrayList<PedidoEntrega>();
        this.listagemProdutos = new ArrayList<Produto>();        
    }

    public ItemPedidoEntrega getItemPedidoEntrega() {
        return itemPedidoEntrega;
    }

    public void setItemPedidoEntrega(ItemPedidoEntrega itemPedidoEntrega) {
        this.itemPedidoEntrega = itemPedidoEntrega;
    }

    public List<ItemPedidoEntrega> getListagemItemPedidoEntrega() {
        return listagemItemPedidoEntrega;
    }

    public void setListagemItemPedidoEntrega(List<ItemPedidoEntrega> listagemItemPedidoEntrega) {
        this.listagemItemPedidoEntrega = listagemItemPedidoEntrega;
    }

    public List<PedidoEntrega> getListagemPedidoEntrega() {
        return listagemPedidoEntrega;
    }

    public void setListagemPedidoEntrega(List<PedidoEntrega> listagemPedidoEntrega) {
        this.listagemPedidoEntrega = listagemPedidoEntrega;
    }

    public List<Produto> getListagemProdutos() {
        return listagemProdutos;
    }

    public void setListagemProdutos(List<Produto> listagemProdutos) {
        this.listagemProdutos = listagemProdutos;
    }

    @Override
    public String toString() {
        return "ItemPedidoEntregaController{" + "itemPedidoEntrega=" + itemPedidoEntrega + ", listagemItemPedidoEntrega=" + listagemItemPedidoEntrega + ", listagemPedidoEntrega=" + listagemPedidoEntrega + ", listagemProdutos=" + listagemProdutos + '}';
    }
    
    
    public String adicionarItemPedidoEntrega(){ 
      
        FacesContext contexto = FacesContext.getCurrentInstance();        
        FacesMessage mensagem;        
      
        String navegacao = "adicionarItem?faces-redirect=true";        
        
        EntityManager em = EntityManagerUtil.getEntityManager();        try {
            em.getTransaction().begin();
            //em.persist(contato);
            this.itemPedidoEntrega = em.merge(itemPedidoEntrega);
            em.getTransaction().commit();
            
            // Definindo a mensagem de retorno com sucesso
            mensagem = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item adicionado.",
                    "Item: " + this.itemPedidoEntrega.getId()+ " cadastrado/atualizado com sucesso!");
        } catch (Exception e) {
            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }
            em.getTransaction().rollback();
//            navegacao = "erro?faces-redirect=true";

            // Definindo a mensagem de retorno com erro
            mensagem = new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro.",
                    "Item: " + this.itemPedidoEntrega.getId() + " não cadastrado/atualizado!");
        } finally {
            em.close();
        }
        
        contexto.addMessage(null, mensagem);  
        
        FacesContext.getCurrentInstance().
                getExternalContext().getFlash().setKeepMessages(true);
        return navegacao;
    }    
    
    
    public void carregarItens() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("BurgerMaxPU");
        EntityManager em = emf.createEntityManager();

        try {
            TypedQuery<ItemPedidoEntrega> query
                    = em.createQuery("Select i from ItemPedidoEntrega i", ItemPedidoEntrega.class);
            this.listagemItemPedidoEntrega = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        } finally {
            em.close();
        }
    }
    
    public String limparSession() {
        // Navegação sem Facelets
        // String navegacao = "index?faces-redirect=true";
        // Navegação com Facelets
        String navegacao = "adicionarItemPedidoEntrega?faces-redirect=true";

        //Contexto da Aplicação
        FacesContext conext = FacesContext.getCurrentInstance();
        //Verifica a sessao e a grava na variavel
        HttpSession session = (HttpSession) conext.getExternalContext().getSession(false);
        //Fecha/Destroi sessao
        session.invalidate();
        // inicializa novo contato
        this.itemPedidoEntrega = new ItemPedidoEntrega();
        return navegacao;
    }
    
    
     public String excluir(ItemPedidoEntrega item) {
        // Para testes locais
        System.out.println(item);
        
        // "Captura" o contexto corrente da aplicação para exibir mensagem
        FacesContext contexto = FacesContext.getCurrentInstance();
        // Utilizado para criar a mensagem à ser exibida
        FacesMessage mensagem;
        // Para navegação com Mensagens
        String navegacao = "pedidoEntregaCadastro?faces-redirect=true";  
        
        // Para navegação sem Mensagens
//        String navegacao = "confirmacao?faces-redirect=true";

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("BurgerMaxPU");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            ItemPedidoEntrega item2 = em.merge(item);
            em.remove(item2);
            em.getTransaction().commit();
            this.itemPedidoEntrega = item2;

            // Definindo a mensagem de retorno com sucesso
            mensagem = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exclusão efetuada.",
                    "Item: " + this.itemPedidoEntrega.getId() + " excluído com sucesso!");

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
                    "Item: " + this.itemPedidoEntrega.getId()+ "não excluído!");
        } finally {
            em.close();
            carregarItens();
        }
        // Adicionando mensagem no contexto para exibição ao usuário.
        contexto.addMessage(null, mensagem);
        return navegacao;
    }
     
     public void carregarProdutos(){
        EntityManager em = EntityManagerUtil.getEntityManager();
        try{
            TypedQuery<Produto> query =
                    em.createQuery("Select p from Produto p order by p.descricao", Produto.class);
           
            this.listagemProdutos = query.getResultList();            
        }catch(Exception e){
            System.out.println("Erro: " + e);
        }finally{
            em.close();
        } 
    }
   
    
   
    
    
    
    
    
}
