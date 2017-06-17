
package br.burgermax.converters;


import br.burgermax.model.Produto;
import br.burgermax.util.EntityManagerUtil;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

@FacesConverter(value="produtoConverter", forClass=Produto.class)
public class ProdutoConverter implements Converter{
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        Produto pro = null;
        EntityManager em = EntityManagerUtil.getEntityManager();
        try{
            if(string!=null){
                pro = em.find(Produto.class, Integer.parseInt(string));                
            }
            System.out.println("Converter: " + pro);
            return pro;
        }catch(Exception e){
            System.out.println("Erro: " + e);
            return null;
        }finally{
            em.close();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        System.out.println("Conversor: getAsString");
        if(o!=null){            
            Produto pro = (Produto) o;
            return String.valueOf(pro.getId());
        }
        return null;
    }
}
