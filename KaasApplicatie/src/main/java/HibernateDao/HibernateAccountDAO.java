package HibernateDao;


import POJO.AccountPOJO;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class HibernateAccountDAO extends GenericDAO{

    private Logger LOGGER = Logger.getLogger(HibernateAccountDAO.class.getName());
        
    public HibernateAccountDAO(EntityManager em) {
        super(em);
    }
 
    @Override
    public List<AccountPOJO> getAll() {
        LOGGER.info("getAllAccount Start");
        em.getTransaction().begin();
        Query query = em.createQuery("FROM AccountPOJO");
        List<AccountPOJO> accounts = query.getResultList();
        em.getTransaction().commit();
        em.close();/////////////////////////////////////////////
        LOGGER.info("getAllAccount End");
        return accounts;
    }

    public List<AccountPOJO> getAccountWithName(String name) {
        LOGGER.info("getAccountWithName Start");
        em.getTransaction().begin();
        Query query = em.createQuery("FROM AccountPOJO WHERE accountname = :name");
        query.setParameter("name", name);
        List<AccountPOJO> accounts = query.getResultList();
        em.getTransaction().commit();
        em.close();////////////////////////////////////////////
        LOGGER.info("getAccountWithName end");
        return accounts;
    }
 
}
