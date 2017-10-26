/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import HibernateDao.GenericDAO;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.stereotype.Component;

/**
 *
 * @author Gerben
 */
@Component
public class HibernateClientDAO extends GenericDAO implements ClientDAOInterface {

    private Logger LOGGER = Logger.getLogger(HibernateClientDAO.class.getName());
   

    public HibernateClientDAO() {
       
    }

    @Override
    public List<ClientPOJO> getAllClient() {
        LOGGER.info("getAllClient Start");
        em.getTransaction().begin();
        Query query = em.createQuery("FROM ClientPOJO");
        List<ClientPOJO> clients = query.getResultList();
        em.getTransaction().commit();
        LOGGER.info("getAllClient End");
        return clients;
    }
     
    @Override
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

    @Override
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

    @Override
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

    @Override
    public Integer addClient(ClientPOJO client) {
        ClientPOJO client2 = super.create(client);
        return client2.getClientID();
    }

    @Override
    public ClientPOJO getClient(ClientPOJO client) {
        return super.findById(ClientPOJO.class, client.getClientID());
    }

    @Override
    public void updateClient(ClientPOJO client) {
        super.update(client);
    }

    @Override
    public void deleteClient(ClientPOJO client) {
        super.delete(ClientPOJO.class, client.getClientID());
    }
    

}
