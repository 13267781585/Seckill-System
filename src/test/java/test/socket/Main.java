package test.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        Socket socket = serverSocket.accept();

        String content = "hello!";

        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        out.write(content.getBytes());


    }
}
