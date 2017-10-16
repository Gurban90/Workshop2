/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HibernateDao;

import POJO.ClientPOJO;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Gerben
 */
public class HibernateClientDAO extends GenericDAO {

    private Logger LOGGER = Logger.getLogger(HibernateClientDAO.class.getName());
   

    public HibernateClientDAO(EntityManager em) {
        super(em);
    }


    @Override
    public List<ClientPOJO> getAll() {
        LOGGER.info("getAllClient Start");
        em.getTransaction().begin();
        Query query = em.createQuery("FROM ClientPOJO");
        List<ClientPOJO> clients = query.getResultList();
        em.getTransaction().commit();
        LOGGER.info("getAllClient End");
        return clients;
    }
     
    public List<ClientPOJO> getClientWithFirstName(String firstname) {
        LOGGER.info("getAccountWithName Start");
        em.getTransaction().begin();
        Query query = em.createQuery("FROM ClientPOJO WHERE firstname = :name");
        query.setParameter("name", firstname);
        List<ClientPOJO> clients = query.getResultList();
        em.getTransaction().commit();
        LOGGER.info("getAccountWithName end");
        return clients;
    }

    public List<ClientPOJO> getClientWithLastName(String lastname) {
        LOGGER.info("getAccountWithName Start");
        em.getTransaction().begin();
        Query query = em.createQuery("FROM ClientPOJO WHERE lastname = :name");
        query.setParameter("name", lastname);
        List<ClientPOJO> clients = query.getResultList();
        em.getTransaction().commit();
        LOGGER.info("getAccountWithName end");
        return clients;
    }

    public List<ClientPOJO> getClientWithEmail(String email) {
        LOGGER.info("getAccountWithName Start");
        em.getTransaction().begin();
        Query query = em.createQuery("FROM ClientPOJO WHERE email = :mail");
        query.setParameter("mail", email);
        List<ClientPOJO> clients = query.getResultList();
        em.getTransaction().commit();
        LOGGER.info("getAccountWithName end");
        return clients;
    }
    

}
