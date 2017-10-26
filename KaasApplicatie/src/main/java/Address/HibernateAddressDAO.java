/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Address;

import HibernateDao.GenericDAO;
import Client.ClientPOJO;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import org.springframework.stereotype.Component;

/**
 *
 * @author Gerben
 */
@Component
public class HibernateAddressDAO extends GenericDAO implements AddressDAOInterface {
    
    private Logger LOGGER = Logger.getLogger(HibernateAddressDAO.class.getName());
    
    
    public HibernateAddressDAO() {
       
    }
   
    @Override
     public List<AddressPOJO> getAllAddress() {
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
      
    @Override
       public List<AddressTypePOJO> getAllAddressType() {
        LOGGER.info("getAllAddressType Start");
        em.getTransaction().begin();
        Query query = em.createQuery("FROM AddressTypePOJO");
        List<AddressTypePOJO> addressTypes = query.getResultList();
        em.getTransaction().commit();
        LOGGER.info("getAllAddressType End");
        return addressTypes;
    }

    @Override
    public Integer addAddress(AddressPOJO adress) {
        AddressPOJO address2 = super.create(adress);
        return address2.getAddressID();
    }


    @Override
    public AddressPOJO getAddress(AddressPOJO address) {
        return super.findById(AddressPOJO.class, address.getAddressID());
    }

    @Override
    public void updateAddress(AddressPOJO address) {
        super.update(address);
    }

    @Override
    public void deleteAddress(AddressPOJO address) {
        super.delete(AddressPOJO.class, address.getAddressID());
    }

    @Override
    public Integer addAddressType(AddressTypePOJO addressType) {
        AddressTypePOJO addresstype2 = super.create(addressType);
        return addresstype2.getAddressTypeID();
    }

    @Override
    public List<AddressPOJO> getAddressWithClient(ClientPOJO client) {
       return getAddressWithClient(client.getClientID());
    }

    @Override
    public AddressTypePOJO getAddressType(AddressTypePOJO addressType) {
        return super.findById(AddressTypePOJO.class, addressType.getAddressTypeID());
    }

    @Override
    public void updateAddressType(AddressTypePOJO addressType) {
        super.update(addressType);
    }

    @Override
    public void deleteAddressType(AddressTypePOJO addressType) {
        super.delete(AddressTypePOJO.class, addressType.getAddressTypeID());
    }
       
      

       
       
      
}
