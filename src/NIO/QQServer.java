package NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class QQServer {
    static byte[] bytes = new byte[1024];
    static List<SocketChannel> list = new ArrayList<>();
    static ByteBuffer byteBuffer = ByteBuffer.allocate(512);
    public static void main(String[] args) throws IOException {
//        ServerSocket serverSocket = new ServerSocket();
//        serverSocket.bind(new InetSocketAddress(8080));
//        while (true) {
//            //阻塞
//            Socket socket = serverSocket.accept();
//            int read = socket.getInputStream().read(bytes);
//            String content = new String(bytes);
//            System.out.println(content);
//        }
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8080));
        serverSocketChannel.configureBlocking(false);
        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();
            if (socketChannel == null) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("no connect");
                for (SocketChannel client : list) {
                    int k = client.read(byteBuffer);
                    if (k > 0) {
                        byteBuffer.flip();
                        System.out.println(byteBuffer.toString());
                    }
                }
            } else {
                System.out.println("conn-------------------");
                socketChannel.configureBlocking(false);
                list.add(socketChannel);
                for (SocketChannel client : list) {
                    int k = client.read(byteBuffer);
                    if (k > 0) {
                        byteBuffer.flip();
                        System.out.println(byteBuffer.toString());
                    }
                }
            }
        }
    }

}
