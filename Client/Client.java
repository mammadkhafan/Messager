package Client;

import java.io.DataInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import Server.ClientMannager;

public class Client {
    Socket socket;
    int port = 9090;
    String host = "127.0.0.1";

    InputStream fromServerStream;
    OutputStream toServerStream;

    DataInputStream reader;
    PrintWriter writer;

    public Client() {
        try {
            socket = new Socket(host, port);

            System.out.println("connected to server ...");

            fromServerStream = socket.getInputStream();
            toServerStream = socket.getOutputStream();

            reader = new DataInputStream(fromServerStream);
            writer = new PrintWriter(toServerStream, true);

            //SET CLIENT NAME
            String serverMessage = reader.readLine();
            System.out.println("Server: " + serverMessage);
            Scanner in = new Scanner(System.in);
            String name = in.nextLine();
            writer.println(name);

            //SET CLIENT PASSWORD
            serverMessage = reader.readLine();
            System.out.println("Server: " + serverMessage);
            in = new Scanner(System.in);
            String password = in.nextLine();
            writer.println(password);

            //GET WELCOME MESSAGE
            System.out.println(reader.readLine());

            while (true) {
                //GET THE MENU TEXT
                String menuText = reader.readLine();
                System.out.println(menuText);
                menuText = reader.readLine();
                System.out.println(menuText);

                //PUSH A COMMAND TO SERVER
                String command = in.nextLine();
                writer.println(command);
                
                if (command.equals(Integer.toString(ClientMannager.Exit_number))) {
                    System.out.println(reader.readLine());
                    break;
                } else {
                    doTask(command);
                }
            }

        } catch (Exception e) {
            System.out.println("An error accured in connecting to Server!");
        }
    }

    public void doTask(String taskType) {
        if (taskType.equals(Integer.toString(ClientMannager.Echo_number))) {
            try {
                //GET QUESTION OF TASK
                System.out.println(reader.readLine());
                Scanner in = new Scanner(System.in);

                //PUSH CLIENT RESPOND TO SERVER
                writer.println(in.nextLine());

                //GET RESPOND OF TASK FROM SERVER
                System.out.println(reader.readLine());
            } catch (Exception e) {
                System.out.println("error in pushToServer method at Client.java");
            }
            
        }
    }

    public static void main(String[] args) {
        new Client();
    }
}
