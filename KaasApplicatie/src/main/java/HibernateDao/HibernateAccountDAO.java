package HibernateDao;


import POJO.AccountPOJO;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class HibernateAccountDAO {

    private Logger LOGGER = Logger.getLogger(HibernateAccountDAO.class.getName());
    private EntityManager em;
    private EntityManagerFactory entityManagerFactory;

    public HibernateAccountDAO() {
        entityManagerFactory = Persistence.createEntityManagerFactory("Hibernate");
        em = entityManagerFactory.createEntityManager();
    }

    public Integer addAccount(AccountPOJO account) {
        LOGGER.info("addAccount Start");
        Integer newID = 0;
        em.getTransaction().begin();
        em.persist(account);
        em.getTransaction().commit();
        newID = account.getAccountID();
        em.close();
        LOGGER.info("addAccount End");
        return newID;

    }

    public List<AccountPOJO> getAllAccount() {
        LOGGER.info("getAllAccount Start");
        em.getTransaction().begin();
        Query query = em.createQuery("FROM AccountPOJO");
        List<AccountPOJO> accounts = query.getResultList();
        em.getTransaction().commit();
        em.close();
        LOGGER.info("getAllAccount End");
        return accounts;
    }

    public AccountPOJO getAccount(AccountPOJO account) {
        LOGGER.info("getAccount Start");
        em.getTransaction().begin();
        AccountPOJO foundaccount = em.find(AccountPOJO.class, account.getAccountID());
        em.getTransaction().commit();
        em.close();
        LOGGER.info("getAccount end");
        return foundaccount;
    }

    public List<AccountPOJO> getAccountWithName(AccountPOJO account) {
        LOGGER.info("getAccountWithName Start");
        Query query = em.createQuery("FROM AccountPOJO WHERE name =" + account.getAccountName());
        List<AccountPOJO> accounts = query.getResultList();
        em.getTransaction().commit();
        em.close();
        LOGGER.info("getAccountWithName end");
        return accounts;
    }

    
    public void updateAccount(AccountPOJO account) {
        LOGGER.info("updateAccount Start");
        em.getTransaction().begin();
        em.merge(account);
        em.getTransaction().commit();
        LOGGER.info("updateAccount end");
    }

    public void deleteAccount(AccountPOJO account) {
        LOGGER.info("deleteAccount Start");
        AccountPOJO foundaccount = em.find(AccountPOJO.class, account.getAccountID());
        em.remove(foundaccount);
        em.getTransaction().commit();
        em.close();
        LOGGER.info("deleteAccount end");
    }

    

    public static void main(String[] args) {
        HibernateAccountDAO dao = new HibernateAccountDAO();
        AccountPOJO pojo = new AccountPOJO();
        Integer nr;
        pojo.setAccountID(1);
        pojo.setAccountName("UpdateStuff");
        pojo.setAccountPassword("passie");
        dao.updateAccount(pojo);
        
        
        dao.entityManagerFactory.close();
    }

}
