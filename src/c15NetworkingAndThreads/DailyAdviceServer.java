package c15NetworkingAndThreads;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class DailyAdviceServer {
    String[] adviceList = {"Take smaller bites", "Go for tight jeans. No they do NOT make yoy look fat.",
            "One word: inappropriate", "Just for today, be honest. Tell your boss what you *really think","You might want to rethink that haircut.",
            "Aaj mausam badiya h :) ."};
    public void go() {
        try {
            ServerSocket serverSock = new ServerSocket(4242);
            
            while(true) {
                Socket sock = serverSock.accept();
                PrintWriter writer = new PrintWriter(sock.getOutputStream());
                String advice = getAdvice();
                writer.println(advice);
                writer.close();
                System.out.println(advice);
            }
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private String getAdvice() {
        int random = (int) (Math.random() * adviceList.length);
        return adviceList[random];
    }
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        DailyAdviceServer server = new DailyAdviceServer();
        server.go();
    }
}
