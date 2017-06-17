package br.burgermax.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class EntityManagerUtil {
    private static EntityManagerFactory emf = null;
    
    public static EntityManager getEntityManager() {
        if (emf == null) {
            try {
                emf = Persistence.createEntityManagerFactory("BurgerMaxPU");
            } catch (Exception e) {
                System.out.println("Erro" + e);
            }
        }
        return emf.createEntityManager();
    }
}
