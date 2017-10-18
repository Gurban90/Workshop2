package Controller;

import Dao.ClientDAO;
import Helper.HibernateDaoFactory;
import Helper.IDCheck;
import HibernateDao.HibernateAccountDAO;
import HibernateDao.HibernateClientDAO;
import Interface.AccountDAOInterface;
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
    private AccountPOJO accountpojo;
    private AccountDAOInterface accountdao;

    public ClientController() {
        clientdao = (HibernateClientDAO) HibernateDaoFactory.getInstance().getDao("client");
        this.clientpojo = new ClientPOJO();
        this.accountpojo = new AccountPOJO();
        accountdao = (HibernateAccountDAO) HibernateDaoFactory.getInstance().getDao("account");
    }

    public ClientController(ClientDAOInterface clientdao) {
        this.clientdao = clientdao;
        this.clientpojo = new ClientPOJO();
    }

    public void newClient(String firstname, String lastname, String email, int accountID) {
        LOGGER.info("newClient start");
        try {
            accountpojo.setAccountID(accountID);
            accountpojo = accountdao.getAccount(accountpojo);
            if (accountpojo.getClient() == null) {
                accountpojo.setAccountID(accountID);
                accountpojo = accountdao.getAccount(accountpojo);
                clientpojo.setAccount(accountpojo);
                clientpojo.setFirstName(firstname);
                clientpojo.setLastName(lastname);
                clientpojo.setEMail(email);
                clientdao.addClient(clientpojo);
                accountpojo.setClient(clientpojo);
                accountdao.updateAccount(accountpojo);
                clientdao.finalize();
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
            clientpojo.setClientID(clientID);
            clientdao.deleteClient(clientpojo);
            clientdao.finalize();
            System.out.println("Client removed.");
        } catch (Exception E) {
            System.out.println("Has to be an existing Client.");
            return;
        }
        LOGGER.info("removeClient end");

    }

    public void getAllClients() {
        LOGGER.info("getallClient end");
        System.out.println(clientdao.getAllClient());
        clientdao.finalize();
        LOGGER.info("getallClient end");

    }

    public String editClient(int clientID, String firstName, String lastName, String eMail) {
        LOGGER.info("edditClient end");
        try {
            clientpojo.setClientID(clientID);
            ClientPOJO client = clientdao.getClient(clientpojo);
            client.setFirstName(firstName);
            client.setLastName(lastName);
            client.setEMail(eMail);
            clientdao.updateClient(client);
            clientdao.finalize();
            LOGGER.info("editClient end");
            return "client eddited";
        } catch (Exception E) {
            return "Client not found.";
        }
    }

    public String editClientFirstName(int clientID, String firstName) {
        LOGGER.info("edit clientFirstName start");
        try {
            clientpojo.setClientID(clientID);
            ClientPOJO client = clientdao.getClient(clientpojo);
            client.setFirstName(firstName);
            clientdao.updateClient(client);
            clientdao.finalize();
            LOGGER.info("editClient First Name end");
            return "Client first name has been edited. ";
        } catch (Exception E) {
            return "Client not found.";
        }
    }

    public String editClientLastName(int clientID, String lastName) {
        LOGGER.info("edit clientFirstName start");
        try {
           clientpojo.setClientID(clientID);
            ClientPOJO client = clientdao.getClient(clientpojo);
            client.setLastName(lastName);
           clientdao.updateClient(client);
            clientdao.finalize();
            LOGGER.info("editClient First Name end");
            return "Client first name has been edited. ";
        } catch (Exception E) {
            return "Client not found.";
        }
    }

    public String editClientEMail(int clientID, String eMail) {
        LOGGER.info("edit clientemail start");
        try {
            clientpojo.setClientID(clientID);
            ClientPOJO client = clientdao.getClient(clientpojo);
            client.setEMail(eMail);
            clientdao.updateClient(client);
            clientdao.finalize();
            LOGGER.info("editClient email end");
            return "Client email has been edited. ";
        } catch (Exception E) {
            return "Client not found.";
        }
    }

    public void findClientWithID(int clientID) {
        LOGGER.info("findclient with id");
        System.out.println(clientdao.getClient(clientpojo));
        clientdao.finalize();
        LOGGER.info("findclient with id");

    }

    public void findClientWithFirstName(String firstName) {
        LOGGER.info("findclient with first name start");
        System.out.println(clientdao.getClientWithFirstName(firstName));
        clientdao.finalize();
        LOGGER.info("findclient with first name end");

    }

    public void findClientWithLastName(String lastName) {
        LOGGER.info("findclient with last name start");
        System.out.println(clientdao.getClientWithLastName(lastName));
        clientdao.finalize();
        LOGGER.info("findclient with last name end");

    }

    public void findClientWithEMail(String eMail) {
        LOGGER.info("findclient with email start");
        System.out.println(clientdao.getClientWithEmail(eMail));
        clientdao.finalize();
        LOGGER.info("findclient with email end");

    }

}
