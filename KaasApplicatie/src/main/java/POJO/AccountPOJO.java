package POJO;

public class AccountPOJO {

    private int accountID;
    private String accountName;
    private String password;
    private int accountStatus;

    public AccountPOJO() {
    }

    public AccountPOJO(Integer accountID, String accountName, String password, Integer accountStatus) {
        this.accountID = accountID;
        this.accountName = accountName;
        this.password = password;
        this.accountStatus = accountStatus;
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
