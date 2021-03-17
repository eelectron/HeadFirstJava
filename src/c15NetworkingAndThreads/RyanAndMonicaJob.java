package c15NetworkingAndThreads;

public class RyanAndMonicaJob implements Runnable{
    private BankAccount account = new BankAccount();
    public void run() {
        for(int i = 0; i < 10; i++) {
            makeWithdrawal(10);
            if(account.getBalance() < 0) {
                System.out.println("Overdrawn !");
            }
        }
    }
    
    private synchronized void makeWithdrawal(int amount) {
        // TODO Auto-generated method stub
        String tName = Thread.currentThread().getName();
        if(account.getBalance() >= amount) {
            System.out.println(tName + " is about to withdraw");
            try {
                Thread.sleep(1000);
                System.out.println(tName + " is going to sleep");
            }catch(InterruptedException ex) {
                ex.printStackTrace();
            }
            
            System.out.println(tName + " woke up !");
            account.withdrawal(amount);
            System.out.println(tName + " completes the withdrawal");
        }
        else {
            System.out.println("Sorry, not enought for " + tName);
        }
    }
    
    public static void main(String[] args) {
        RyanAndMonicaJob job = new RyanAndMonicaJob();
        Thread one = new Thread(job);
        Thread two = new Thread(job);
        one.setName("Ryan");
        two.setName("Monica");
        one.start();
        two.start();
    }
}
