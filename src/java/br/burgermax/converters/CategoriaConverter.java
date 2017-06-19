
package br.burgermax.converters;


import br.burgermax.model.Categoria;
import br.burgermax.util.EntityManagerUtil;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

@FacesConverter(value="categoriaConverter", forClass=Categoria.class)
public class CategoriaConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        Categoria cat = null;
        EntityManager em = EntityManagerUtil.getEntityManager();
        try{
            if(string!=null){
                cat = em.find(Categoria.class, Integer.parseInt(string));                
            }
            System.out.println("Converter: " + cat);
            return cat;
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
            Categoria cat = (Categoria) o;
            return String.valueOf(cat.getId());
            //return String.valueOf(((Contato) o).getCodigo());    
        }
        return null;
    }
    
}
