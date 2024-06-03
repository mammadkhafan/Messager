package Client;

import java.io.DataInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

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

            String serverMessage = reader.readLine();
            System.out.println("Server: " + serverMessage);
            Scanner in = new Scanner(System.in);
            String name = in.nextLine();
            writer.println(name);

            serverMessage = reader.readLine();
            System.out.println("Server: " + serverMessage);
            in = new Scanner(System.in);
            String password = in.nextLine();
            writer.println(password);

        } catch (Exception e) {
            System.out.println("An error accured in connecting to Server!");
        }
    }

    public static void main(String[] args) {
        new Client();
    }
}
