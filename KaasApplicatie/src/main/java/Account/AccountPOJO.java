package Account;

import Client.ClientPOJO;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.springframework.stereotype.Component;

@Entity
@Table(name="Account")
@Component
public class AccountPOJO {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int accountID;
    private String accountName;
    private String password;
    private int accountStatus;
    
    @OneToOne
    @JoinColumn(name="ClientID", referencedColumnName="ClientID")
    private ClientPOJO client;



    public AccountPOJO() {
    }

    public AccountPOJO(Integer accountID, String accountName, String password, Integer accountStatus) {
        this.accountID = accountID;
        this.accountName = accountName;
        this.password = password;
        this.accountStatus = accountStatus;
    }

    public ClientPOJO getClient() {
        return client;
    }

    public void setClient(ClientPOJO client) {
        this.client = client;
    }
    
    

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountPassword() {
        return password;
    }

    public void setAccountPassword(String password) {
        this.password = password;
    }

    public int getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(int accountStatus) {
        this.accountStatus = accountStatus;
    }

    @Override
    public String toString() {
        return "AccountID: " + accountID + ", AccountName: " + accountName + " Password: " + password + ", AccountStatus: " + accountStatus;
    }

}
