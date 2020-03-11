import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class SelectorX {

    public static void main(String[] args) {
        try {
            Selector selector = Selector.open();

            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress("http://jenkov.com", 80));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
