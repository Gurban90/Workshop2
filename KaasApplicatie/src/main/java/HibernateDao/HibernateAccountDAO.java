package HibernateDao;

import Interface.AccountDAOInterface;
import POJO.AccountPOJO;
import POJO.ClientPOJO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class HibernateAccountDAO extends GenericDAO implements AccountDAOInterface {

    private Logger LOGGER = Logger.getLogger(HibernateAccountDAO.class.getName());

    public HibernateAccountDAO(EntityManager em) {
        super(em);
    }

    @Override
    public List<AccountPOJO> getAllAccount() {
        LOGGER.info("getAllAccount Start");
        em.getTransaction().begin();
        Query query = em.createQuery("FROM AccountPOJO");
        List<AccountPOJO> accounts = query.getResultList();
        em.getTransaction().commit();
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
        LOGGER.info("getAccountWithName end");
        return accounts;
    }

    @Override
    public Integer addAccount(AccountPOJO account) {
        AccountPOJO account2 = super.create(account);
        return account2.getAccountID();
    }

    @Override
    public AccountPOJO getAccount(AccountPOJO account) {
        return super.findById(AccountPOJO.class, account.getAccountID());
    }

    @Override
    public List<AccountPOJO> getAccountWithName(AccountPOJO account) {
        return getAccountWithName(account.getAccountName());
    }

    @Override
    public void updateAccount(AccountPOJO account) {
        super.update(account);
    }

    @Override
    public void deleteAccount(AccountPOJO account) {
        super.delete(AccountPOJO.class, account.getAccountID());
    }

    @Override
    public void finalize() {
        super.finalize();
    }

    @Override
    public List<AccountPOJO> getAccountsWithClients() {
        LOGGER.info("getAccountsWithClients Start");
        em.getTransaction().begin();
        Query query = em.createQuery("FROM AccountPOJO WHERE ClientID IS NOT NULL");
        List<AccountPOJO> accounts = query.getResultList();
        em.getTransaction().commit();
        LOGGER.info("getAccountsWithClients End");
        return accounts;
    }

}


