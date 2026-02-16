package Classes;
public class SavingAccount extends Account{
    private final float RATE;
    public SavingAccount(){
        super("135 000 795");
        RATE = 0.05f;
    }
    @Override
    public boolean deposit(double amount) {
        if (amount >= 200) {
            amount += amount * RATE;
            amount = Math.round(amount * 100.0) / 100.0;
        }
        return super.deposit(amount);
    }
}
