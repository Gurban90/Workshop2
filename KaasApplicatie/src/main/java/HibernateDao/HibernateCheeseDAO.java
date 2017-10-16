/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HibernateDao;

import POJO.CheesePOJO;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Gerben
 */
public class HibernateCheeseDAO extends GenericDAO {

   private Logger LOGGER = Logger.getLogger(HibernateCheeseDAO.class.getName());
   
    
    public HibernateCheeseDAO(EntityManager em) {
        super(em);
    }


    @Override
    public List<CheesePOJO> getAll() {
       LOGGER.info("getAllCheese Start");
        em.getTransaction().begin();
        Query query = em.createQuery("FROM CheesePOJO");
        List<CheesePOJO> cheeses = query.getResultList();
        em.getTransaction().commit();
        LOGGER.info("getAllCheese End");
        return cheeses;
    }
    
    public List<CheesePOJO> getCheeseWithName(String name) {
        LOGGER.info("getCheeseWithName Start");
        em.getTransaction().begin();
        Query query = em.createQuery("FROM CheesePOJO WHERE cheesename = :name");
        query.setParameter("name", name);
        List<CheesePOJO> cheeses = query.getResultList();
        em.getTransaction().commit();
        LOGGER.info("getCheeseWithName end");
        return cheeses;
    }
    
}
