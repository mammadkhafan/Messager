package Server;

import java.io.DataInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;


public class ClientHandler /*extends Thread*/implements Runnable{
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

    public ClientHandler(Server server, Socket clientSocket) {
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

            //GET NAME FROM CLIENT
            writer.println("Enter name:");
            String name = reader.readLine();
            currentUser = new User(name);
            //SYNCHRONIZED BLOCK
            synchronized (server){
                server.users.add(currentUser);
            }
            System.out.println(createCommunication("Enter name", name));

            //GET PASSWORD FROM CLIENT
            writer.println("Enter password:");
            String password = reader.readLine();
            System.out.println(createCommunication("Enter password", password));
            currentUser.setPassword(password);
            writer.println("Hi " + name + ", inseart a number");
            System.out.println("Account added to data center successfully!");


            while (true) {
                //MENU BAR
                menu();
            }
        } catch (Exception e) {
            // System.out.println("error in run at Cliet mannager");
            e.printStackTrace();
        }
    }

    private void menu() {
        writer.println("1)Echo");
        writer.println("2)Exit");

        String command = "EMPTY_COMMAND";
        try {
            //GET COMMAND FROM CLIENT
            command = reader.readLine();
        } catch (Exception e) {
            System.out.println("error in menu at ClientMannager.java");
        }
        
        //CALL THE RELATIVE METHOD
        if (command.contains(Integer.toString(Echo_number))) {
            //CALL ECHO
            Echo();
        } else if (command.contains(Integer.toString(Exit_number))) {
            // CALL EXIT
            Exit();
        } else {
            //WHEN CLIENT SEND INVALID NUMBER
            writer.println("Inseart valid number.");
            menu();
        }
    }

    private void Echo() {
        writer.println("What is your text?");
        String clientText = "EMPTY_TEXT";

        try {
            //GET A TEXT FROM CLIENT TO DO ECHO
            clientText = reader.readLine();
        } catch (Exception e) {
            System.out.println("error in Echo method at ClientMannager.java");
        } finally {
            //WRITE TEXT AS ITS ECHO
            writer.println("Echo: " + clientText);
        }

        //PRINT IN SERVER TERMINALL TO SAVE HISTORY
        System.out.println(currentUser.getName() + " => [Echo] => " + clientText);
    }

    private void Exit() {
        //PUSH CLEINT OUT :)
        writer.println("Good Bye " + currentUser.getName());

        //PRINT IN SERVER TERMINALL TO SAVE HISTORY
        System.out.println(currentUser.getName() + " => [Exit]");
    }

    private String createCommunication(String serverMessage, String clientMessage) {
        //THIS IS VERY USEFUL
        return "[Server) " + serverMessage + "..." + currentUser.getName() + ") " + clientMessage + "]";//Ex:[Server) Enter name...Bob) Bob]
    }

    public boolean isValidTaskType(String taskType) {
        if (taskType.equals(Integer.toString(ClientHandler.Echo_number))
        ||  taskType.equals(Integer.toString(ClientHandler.Exit_number))) {
            return true;
        } else {
            return false;
        }
    }
    
}
