import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;

public class Client {
    private Socket clientSocket;
    private InputStream in;

    private void startConnection(String serverName,int port) throws IOException {
        InetAddress serverAdress = InetAddress.getByName(serverName);
        clientSocket = new Socket(serverAdress,port);

        in = clientSocket.getInputStream();
    }
    private void getImage() throws IOException {
        byte[] sizeAr = new byte[4];
        in.read(sizeAr);
        int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();

        byte[] imageAr = new byte[size];
        in.read(imageAr);

        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));
        ImageIO.write(image,"jpg",new File("receiveData/1.jpg"));
    }

    private void stopConnection() throws IOException {
        in.close();
        clientSocket.close();
    }

    public static void main(String[] args) throws IOException {
        String serverName = "localhost";
        Client client = new Client();
        client.startConnection(serverName,10000);
        client.getImage();
        client.stopConnection();
    }


}
