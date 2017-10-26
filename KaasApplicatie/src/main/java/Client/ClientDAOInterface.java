package Client;

import java.util.List;

public interface ClientDAOInterface {

    public Integer addClient(ClientPOJO client);

    public List<ClientPOJO> getAllClient();

    public ClientPOJO getClient(ClientPOJO client);

    public List<ClientPOJO> getClientWithFirstName(String FirstName);

    public List<ClientPOJO> getClientWithLastName(String LastName);

    public List<ClientPOJO> getClientWithEmail(String Email);

    public void updateClient(ClientPOJO client);

    public void deleteClient(ClientPOJO client);
    
    public void finalize();

}
