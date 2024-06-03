package Server;

import java.io.DataInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import Client.Client;

public class ClientMannager extends Thread/*implements Runnable*/{
    //------------------------
    public final static int Echo_number = 1;
    public final static int Exit_number = 2;
    //------------------------
    User currentUser;
    Socket clientSocket;
    Server server;

	InputStream fromClientStream;
	OutputStream toClientStream;

	DataInputStream reader;
	PrintWriter writer;

    public ClientMannager(Server server, Socket clientSocket) {
        this.server = server;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            fromClientStream = clientSocket.getInputStream();

            toClientStream = clientSocket.getOutputStream();

            reader = new DataInputStream(fromClientStream);
            writer = new PrintWriter(toClientStream, true);

            writer.println("Enter name:");

            String name = reader.readLine();
            currentUser = new User(name);
            //SYNCHRONIZED BLOCK
            synchronized (server){
                server.users.add(currentUser);
            }
            System.out.println(createCommunication("Enter name", name));

            writer.println("Enter password:");

            String password = reader.readLine();
            System.out.println(createCommunication("Enter password", password));

            currentUser.setPassword(password);
            writer.println("Hi " + name + ", inseart a number");
            System.out.println("Account added to data center successfully!");
            while (true) {
                menu();
            }
        } catch (Exception e) {
            // System.out.println("error in run at Cliet mannager");
            e.printStackTrace();
        }
    }

    public void menu() {
        writer.println("1)Echo");
        writer.println("2)Exit");

        String command = "EMPTY_COMMAND";
        try {
            //GIT COMMAND FROM CLIENT
            command = reader.readLine();
        } catch (Exception e) {
            // System.out.println("error in menu at ClientMannager.java");
        }
        
        //CALL THE RELATIVE METHOD
        if (command.contains(Integer.toString(Echo_number))) {
            Echo();
        } else if (command.contains(Integer.toString(Exit_number))) {
            Exit();
        } else {
            writer.println("Inseart valid number.");
            menu();
        }
    }

    public void Echo() {
        writer.println("What is your text?");
        String clientText = "EMPTY_TEXT";

        try {
            clientText = reader.readLine();
        } catch (Exception e) {
            System.out.println("error in Echo method at ClientMannager.java");
        } finally {
            writer.println("Echo: " + clientText);
        }

        System.out.println(currentUser.getName() + " => [Echo] => " + clientText);
    }

    public void Exit() {
        writer.println("Good Bye " + currentUser.getName());
        System.out.println(currentUser.getName() + " => [Exit]");
    }

    public String createCommunication(String serverMessage, String clientMessage) {
        return "[Server) " + serverMessage + "..." + currentUser.getName() + ") " + clientMessage + "]";//Ex:[Server) Enter name...Bob) Bob]
    }
    
}
