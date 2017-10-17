package Controller;

import Dao.ClientDAO;
import Helper.HibernateDaoFactory;
import Helper.IDCheck;
import HibernateDao.HibernateAccountDAO;
import HibernateDao.HibernateClientDAO;
import Interface.ClientDAOInterface;
import POJO.AccountPOJO;
import POJO.ClientPOJO;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.PersistenceException;

public class ClientController {

    private static final Logger LOGGER = Logger.getLogger(ClientController.class.getName());
    private ClientDAOInterface clientdao;
    private ClientPOJO clientpojo;
    private HibernateClientDAO hibClientDAO;
    private AccountPOJO accountpojo;
    private HibernateAccountDAO hibAccountDAO;

    public ClientController() {
        this.hibClientDAO = (HibernateClientDAO) HibernateDaoFactory.getInstance().getDao("client");
        this.clientpojo = new ClientPOJO();
        this.accountpojo = new AccountPOJO();
        this.hibAccountDAO = (HibernateAccountDAO) HibernateDaoFactory.getInstance().getDao("account");
    }

    public ClientController(ClientDAOInterface clientdao) {
        this.clientdao = clientdao;
        this.clientpojo = new ClientPOJO();
    }

    public void newClient(String firstname, String lastname, String email, int accountID) {
        LOGGER.info("newClient start");
        try {
            accountpojo = hibAccountDAO.findById(AccountPOJO.class, accountID);
            if (accountpojo.getClient() == null) {
                accountpojo = hibAccountDAO.findById(AccountPOJO.class, accountID);
                clientpojo.setAccount(accountpojo);
                clientpojo.setFirstName(firstname);
                clientpojo.setLastName(lastname);
                clientpojo.setEMail(email);
                hibClientDAO.create(clientpojo);
                accountpojo.setClient(clientpojo);
                hibAccountDAO.update(clientpojo);
                hibClientDAO.finalize();
                System.out.println("New Client added with the ClientID of: " + clientpojo.getClientID());
                LOGGER.info("newClient end");
            } else {
                System.out.println("Account is allready linked with other Client");
            }
        } catch (PersistenceException E) {
            System.out.println("Account has to exist.");
        }

    }

    public void removeClient(int clientID) {
        LOGGER.info("removeClient start");
        IDCheck check = new IDCheck();
        if (check.checkClientOrder(clientID)) {
            System.out.println("Cheese is being ordered.");
            return;
        }
        try {
            hibClientDAO.delete(ClientPOJO.class, clientID);
            hibClientDAO.finalize();
            System.out.println("Client removed.");
        } catch (Exception E) {
            System.out.println("Has to be an existing Client.");
            return;
        }
        LOGGER.info("removeClient end");

    }

    public void getAllClients() {
        LOGGER.info("getallClient end");
        System.out.println(hibClientDAO.getAll());
        hibClientDAO.finalize();
        LOGGER.info("getallClient end");

    }

    public String editClient(int clientID, String firstName, String lastName, String eMail) {
        LOGGER.info("edditClient end");
        try {
            ClientPOJO client = hibClientDAO.findById(ClientPOJO.class, clientID);
            client.setFirstName(firstName);
            client.setLastName(lastName);
            client.setEMail(eMail);
            hibClientDAO.update(client);
            hibClientDAO.finalize();
            LOGGER.info("editClient end");
            return "client eddited";
        } catch (Exception E) {
            return "Client not found.";
        }
    }

    public String editClientFirstName(int clientID, String firstName) {
        LOGGER.info("edit clientFirstName start");
        try {
            ClientPOJO client = hibClientDAO.findById(ClientPOJO.class, clientID);
            client.setFirstName(firstName);
            hibClientDAO.update(client);
            hibClientDAO.finalize();
            LOGGER.info("editClient First Name end");
            return "Client first name has been edited. ";
        } catch (Exception E) {
            return "Client not found.";
        }
    }

    public String editClientLastName(int clientID, String lastName) {
        LOGGER.info("edit clientFirstName start");
        try {
            ClientPOJO client = hibClientDAO.findById(ClientPOJO.class, clientID);
            client.setLastName(lastName);
            hibClientDAO.update(client);
            hibClientDAO.finalize();
            LOGGER.info("editClient First Name end");
            return "Client first name has been edited. ";
        } catch (Exception E) {
            return "Client not found.";
        }
    }

    public String editClientEMail(int clientID, String eMail) {
        LOGGER.info("edit clientemail start");
        try {
            ClientPOJO client = hibClientDAO.findById(ClientPOJO.class, clientID);
            client.setEMail(eMail);
            hibClientDAO.update(client);
            hibClientDAO.finalize();
            LOGGER.info("editClient email end");
            return "Client email has been edited. ";
        } catch (Exception E) {
            return "Client not found.";
        }
    }

    public void findClientWithID(int clientID) {
        LOGGER.info("findclient with id");
        System.out.println(hibClientDAO.findById(ClientPOJO.class, clientID));
        hibClientDAO.finalize();
        LOGGER.info("findclient with id");

    }

    public void findClientWithFirstName(String firstName) {
        LOGGER.info("findclient with first name start");
        System.out.println(hibClientDAO.getClientWithFirstName(firstName));
        hibClientDAO.finalize();
        LOGGER.info("findclient with first name end");

    }

    public void findClientWithLastName(String lastName) {
        LOGGER.info("findclient with last name start");
        System.out.println(hibClientDAO.getClientWithLastName(lastName));
        hibClientDAO.finalize();
        LOGGER.info("findclient with last name end");

    }

    public void findClientWithEMail(String eMail) {
        LOGGER.info("findclient with email start");
        System.out.println(hibClientDAO.getClientWithEmail(eMail));
        hibClientDAO.finalize();
        LOGGER.info("findclient with email end");

    }

}
