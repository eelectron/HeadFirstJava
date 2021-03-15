
public class ThreadTester {
    public static void main(String[] args) {
        Runnable job = new MyRunnable();
        Thread myThread = new Thread(job);
        myThread.start();
        System.out.println("back in main");
    }
}
