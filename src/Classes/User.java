package Classes;

public class User {
    private Account savingAccount;
    private Account checkingAccount;
    public boolean createSavingAccount(){
        if(savingAccount != null) return false;
        savingAccount = new SavingAccount();
        return true;
    }
    public boolean createCheckingAccount(){
        if(checkingAccount != null) return false;
        checkingAccount = new CheckingAccount();
        return true;
    }
}
