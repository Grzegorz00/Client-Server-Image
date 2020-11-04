import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;

public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private BufferedReader in;
    private OutputStream out;

    private void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();

        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = clientSocket.getOutputStream();
    }

    private void sendImage(String path) throws IOException {
        BufferedImage image = ImageIO.read(new File(path));
        ByteArrayOutputStream boas = new ByteArrayOutputStream();

        ImageIO.write(image,"jpg",boas);
        byte[] size = ByteBuffer.allocate(4).putInt(boas.size()).array();
        out.write(size);
        out.write(boas.toByteArray());
        out.flush();
    }

    private void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.start(10000);
        server.sendImage("data/1.jpg");
        server.stop();
    }

}
