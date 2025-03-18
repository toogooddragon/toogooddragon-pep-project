package Service;


import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        this.accountDAO = new AccountDAO();
    }
    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }
    

    public Account createAccount(Account account){
        if(account.getUsername().length() <= 0 ||
            account.getPassword().length() <= 4 ||
            accountDAO.getAccount(account) != null) return null;

        return accountDAO.addAccount(account);
    }

    public Account getAccount(Account account){
        return accountDAO.getAccount(account);
    }
}