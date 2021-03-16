package networkingAndThreads;

public class BankAccount {
    private int balance = 100;
    public int getBalance() {
        return balance;
    }
    
    public void withdrawal(int amount) {
        balance = balance - amount;
    }
}
