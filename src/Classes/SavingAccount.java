package Classes;
public class SavingAccount extends Account{
    private final float RATE;
    public SavingAccount(){
        super("666 666 666");
        RATE = 0.05f;
    }
    @Override
    public boolean withdraw(double amount) {
        if(amount >= 200) amount += amount * RATE;
        return super.withdraw(amount);
    }
}
