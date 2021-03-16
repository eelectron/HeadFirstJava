package networkingAndThreads;

public class RunThreads implements Runnable{
    public void run() {
        for(int i = 0; i < 64; i++) {
            String name = Thread.currentThread().getName();
            System.out.println(name + " is running.");
        }
    }
    
    public static void main(String[] args) {
        RunThreads job = new RunThreads();
        Thread alpha = new Thread(job);
        Thread beta = new Thread(job);
        alpha.setName("Alpha thread");
        beta.setName("Beta thread");
        alpha.start();
        beta.start();
    }
}
