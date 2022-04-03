package test.socket;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost",8080);
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        String str = "client!";
        while(true)
        {
            out.write(str.getBytes());
            out.flush();
            System.out.println(in.read());
        }
    }
}
