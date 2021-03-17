package c15NetworkingAndThreads;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class VerySimpleChatServer {
    ArrayList<PrintWriter> clientOutputStreams;
    
    public class ClientHandler implements Runnable{
        BufferedReader reader;
        Socket sock;
        public ClientHandler(Socket client) {
            try {
                sock = client;
                InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(isReader);
            }catch(Exception ex) {
                ex.printStackTrace();
            }
        }
        
        public void run() {
            String message;
            try {
                while((message = reader.readLine()) != null) {
                    System.out.println("read " + message);
                    tellEveryone(message);
                }
            }catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public static void main(String[] args) {
        VerySimpleChatServer server = new VerySimpleChatServer();
        server.go();
    }
    
    public void go() {
        clientOutputStreams = new ArrayList<>();
        
        try {
            ServerSocket serverSock = new ServerSocket(5000);
            while(true) {
                Socket clientSocket = serverSock.accept();
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                clientOutputStreams.add(writer);
                
                Thread t = new Thread(new ClientHandler(clientSocket));
                t.start();
                
                System.out.println("got a connection ");
                
            }
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void tellEveryone(String message) {
        int n = clientOutputStreams.size();
        for(int i = 0; i < n; i++) {
            try {
                PrintWriter writer = (PrintWriter)clientOutputStreams.get(i);
                writer.println(message);
                writer.flush();
            }catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
