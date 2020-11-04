import java.io.*;
import java.net.*;

public class Client {
    private Socket clientSocket;
    private BufferedWriter out;
    private BufferedReader in;

    public void startConnection(String serverName,int port) throws IOException {
        InetAddress serverAdress = InetAddress.getByName(serverName);
        clientSocket = new Socket(serverAdress,port);

        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out.write("elo");
        out.newLine();
        out.flush();
        System.out.println(in.readLine());
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    public static void main(String[] args) throws IOException {
        String serverName = "localhost";
        Client client = new Client();
        client.startConnection(serverName,10000);
        client.stopConnection();
    }
}
