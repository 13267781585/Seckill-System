package test.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(8080);
        Socket socket = serverSocket.accept();

        String content = "hello!";

        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        while(true)
        {
            out.write(content.getBytes());
            out.flush();
            System.out.println(in.read());
            Thread.sleep(1000000000);
        }


    }
}
