package Controller;

import Dao.ClientDAO;
import Interface.ClientDAOInterface;
import POJO.ClientPOJO;
import java.util.List;
import java.util.logging.Logger;

public class ClientController {

    private static final Logger LOGGER = Logger.getLogger(ClientController.class.getName());
    private ClientDAOInterface clientdao;
    private ClientPOJO clientpojo;

    public ClientController() {
        this.clientdao = new ClientDAO();
        this.clientpojo = new ClientPOJO();
    }

    public ClientController(ClientDAOInterface clientdao) {
        this.clientdao = clientdao;
        this.clientpojo = new ClientPOJO();
    }

    public int newClient(String firstname, String lastname, String email) {
        LOGGER.info("newClient start");

        clientpojo.setFirstName(firstname);
        clientpojo.setLastName(lastname);
        clientpojo.setEMail(email);

        LOGGER.info("newClient end");

        return clientdao.addClient(clientpojo);

    }

    public String removeClient(int clientID, String anwser) {

        LOGGER.info("removeClient start");

        if (anwser.equals("Y") || anwser.equals("Yes") || anwser.equals("y") || anwser.equals("yes")) {
            clientpojo.setClientID(clientID);
            clientdao.deleteClient(clientpojo);
            LOGGER.info("removeClient end");
            return "client removed";

        } else {
            LOGGER.info("removeClient end");
            return "No client removed";

        }

    }

    public String editClient(int clientID, String firstName, String lastName, String eMail) {
        LOGGER.info("edditClient end");
        clientpojo.setClientID(clientID);
        clientdao.getClient(clientpojo);

        clientpojo.setFirstName(firstName);
        clientpojo.setLastName(lastName);
        clientpojo.setEMail(eMail);
        clientdao.updateClient(clientpojo);
        LOGGER.info("editClient end");
        return "client eddited";
    }

    public List<ClientPOJO> getAllClients() {
        LOGGER.info("getallClient end");
        LOGGER.info("getallClient end");
        return clientdao.getAllClient();

    }

    public String editClientFirstName(int clientID, String firstName) {
        LOGGER.info("edit clientFirstName start");
        clientpojo.setClientID(clientID);

        ClientPOJO searchedClient = clientdao.getClient(clientpojo);
        searchedClient.setFirstName(firstName);
        clientdao.updateClient(searchedClient);

        LOGGER.info("editClient First Name end");
        return "Client first name has been edited. ";

    }

    public String editClientLastName(int clientID, String lastName) {
        LOGGER.info("edit clientFirstName start");
        clientpojo.setClientID(clientID);

        ClientPOJO searchedClient = clientdao.getClient(clientpojo);
        searchedClient.setLastName(lastName);
        clientdao.updateClient(searchedClient);

        LOGGER.info("editClient First Name end");
        return "Client first name has been edited. ";
    }

    public String editClientEMail(int clientID, String eMail) {
        LOGGER.info("edit clientemail start");
        clientpojo.setClientID(clientID);

        ClientPOJO searchedClient = clientdao.getClient(clientpojo);
        searchedClient.setEMail(eMail);
        clientdao.updateClient(searchedClient);

        LOGGER.info("editClient email end");
        return "Client email has been edited. ";
    }

    public ClientPOJO findClientWithID(int clientID) {
        LOGGER.info("findclient with id");
        clientpojo.setClientID(clientID);
        ClientPOJO returnedClient = clientdao.getClient(clientpojo);
        LOGGER.info("findclient with id");
        return returnedClient;

    }

    public List<ClientPOJO> findClientWithFirstName(String firstName) {
        LOGGER.info("findclient with first name start");

        List<ClientPOJO> returnedClient = clientdao.getClientWithFirstName(firstName);
        LOGGER.info("findclient with first name end");
        return returnedClient;

    }

    public List<ClientPOJO> findClientWithLastName(String lastName) {
        LOGGER.info("findclient with last name start");

        List<ClientPOJO> returnedClient = clientdao.getClientWithLastName(lastName);
        LOGGER.info("findclient with last name end");
        return returnedClient;

    }

    public List<ClientPOJO> findClientWithEMail(String eMail) {
        LOGGER.info("findclient with email start");

        List<ClientPOJO> returnedClient = clientdao.getClientWithEmail(eMail);
        LOGGER.info("findclient with email end");
        return returnedClient;

    }

}
