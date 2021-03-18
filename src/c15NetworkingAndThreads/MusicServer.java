package c15NetworkingAndThreads;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MusicServer {
    List<ObjectOutputStream> clients;
    public static void main(String[] args) {
        MusicServer server = new MusicServer();
        server.go();
    }
    
    public class ClientHandler implements Runnable{
        ObjectInputStream in;
        public ClientHandler(Socket client) {
            try {
                in = new ObjectInputStream(client.getInputStream());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        @Override
        public void run() {
            // TODO Auto-generated method stub
            Object o1 = null, o2 = null;
            try {
                while((o1 = in.readObject()) != null) {
                    o2 = in.readObject();
                    System.out.println("read two object");
                    tellEveryOne(o1, o2);
                }
            }catch(Exception ex) {
                System.out.println("Could not read the data from client " + this.getClass());
            }
            
        }
    }
    
    public void go() {
        clients = new ArrayList<>();
        try {
            ServerSocket serverSock = new ServerSocket(4242);
            while(true) {
                System.out.println("Server is running ...");
                Socket clientSocket = serverSock.accept();
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                clients.add(out);
                
                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();
                System.out.println("a client connected to server");
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void tellEveryOne(Object one, Object two) {
        for(int i = 0; i < clients.size(); i++) {
            ObjectOutputStream out = (ObjectOutputStream)clients.get(i);
            try {
                out.writeObject(one);
                out.writeObject(two);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        }
    }
}
