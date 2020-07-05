package NIO;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("127.0.0.1", 8080);
        Scanner scanner = new Scanner(System.in);
        String text = scanner.next();
        socket.getOutputStream().write(text.getBytes());

    }
}
