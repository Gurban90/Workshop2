/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HibernateDao;

import POJO.AddressPOJO;
import POJO.AddressTypePOJO;
import POJO.ClientPOJO;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Gerben
 */
public class HibernateAddressDAO extends GenericDAO {
    
    private Logger LOGGER = Logger.getLogger(HibernateAccountDAO.class.getName());
    private EntityManager em;
    
    public HibernateAddressDAO(EntityManager em) {
        super(em);
    }
    
    @Override
     public List<AddressPOJO> getAll() {
        LOGGER.info("getAllClient Start");
        em.getTransaction().begin();
        Query query = em.createQuery("FROM AddressPOJO");
        List<AddressPOJO> addresses = query.getResultList();
        em.getTransaction().commit();
        LOGGER.info("getAllClient End");
        return addresses;
    }
    
      public List<AddressPOJO> getAddressWithClient(int clientid) {
        LOGGER.info("getAddressWithClient Start");
        em.getTransaction().begin();
        Query query = em.createQuery("FROM AddressPOJO WHERE ClientID = :id");
        query.setParameter("id", clientid);
        List<AddressPOJO> addresses = query.getResultList();
        em.getTransaction().commit();
        LOGGER.info("getAddressWithClient end");
        return addresses;
    }
      
       public List<AddressTypePOJO> getAllAddressType() {
        LOGGER.info("getAllAddressType Start");
        em.getTransaction().begin();
        Query query = em.createQuery("FROM AddressTypePOJO");
        List<AddressTypePOJO> addressTypes = query.getResultList();
        em.getTransaction().commit();
        LOGGER.info("getAllAddressType End");
        return addressTypes;
    }
      
}
