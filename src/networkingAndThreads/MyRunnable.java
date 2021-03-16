package networkingAndThreads;

public class MyRunnable implements Runnable{

    @Override
    public void run() {
        // TODO Auto-generated method stub
        go();
    }

    public void go() {
        doMore();
    }
    
    public void doMore() {
        System.out.println("top of the stack");
    }
}
