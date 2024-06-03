package Server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {
    ServerSocket socket;
    int port = 9090;

    public ArrayList<User> users = new ArrayList<User>();
    
    public Server() {
        
        try {
            socket = new ServerSocket(port);

            System.out.println("---Server Created---");

            while (true) {
                Socket clientSocket = socket.accept();

                System.out.println("New Client joined!");

                Thread thread = new Thread(new ClientMannager(this, clientSocket));

                thread.start();
            }
        } catch (Exception e) {
            System.out.println("An error acurred in creating the server!");
        }
    }


    public static void main(String[] args) {
        new Server();
    }
}
