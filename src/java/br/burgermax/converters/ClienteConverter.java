
package br.burgermax.converters;


import br.burgermax.model.Cliente;
import br.burgermax.util.EntityManagerUtil;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

@FacesConverter(value="clienteConverter", forClass=Cliente.class)
public class ClienteConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        Cliente cli = null;
        EntityManager em = EntityManagerUtil.getEntityManager();
        try{
            if(string!=null){
                cli = em.find(Cliente.class, Integer.parseInt(string));                
            }
            System.out.println("Converter: " + cli);
            return cli;
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
            Cliente cli = (Cliente) o;
            return String.valueOf(cli.getId());
            //return String.valueOf(((Contato) o).getCodigo());    
        }
        return null;
    }
    
}
