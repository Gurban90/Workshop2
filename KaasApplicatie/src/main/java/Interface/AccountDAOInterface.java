package Interface;

import POJO.AccountPOJO;
import POJO.ClientPOJO;
import java.util.List;

public interface AccountDAOInterface {

    public Integer addAccount(AccountPOJO account);

    public List<AccountPOJO> getAllAccount();

    public AccountPOJO getAccount(AccountPOJO account);

    public List<AccountPOJO> getAccountWithName(AccountPOJO account);

    public void updateAccount(AccountPOJO account);

    public void deleteAccount(AccountPOJO account);

    public List<AccountPOJO> getAccountsWithClients();

    public void finalize();

}
