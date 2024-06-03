package Server;

import java.io.DataInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientMannager extends Thread/*implements Runnable*/{
    Socket clientSocket;

	InputStream fromClientStream;
	OutputStream toClientStream;

	DataInputStream reader;
	PrintWriter writer;

    public ClientMannager(Socket clientSocket) {
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
            System.out.println("Server)Enter name: " + name);

            writer.println("Enter password:");

            String password = reader.readLine();
            System.out.println("Server)Enter password: " + password);

            while (true) {

            }
        } catch (Exception e) {
            System.out.println("error acurred in Cliet mannager");
        }
    }
    
}
