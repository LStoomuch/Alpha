import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketChannelInput {

    public static void main(String[] args) {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress( 9999));

            ByteBuffer buffer = ByteBuffer.allocate(48);
            int byteRead = socketChannel.read(buffer);

            while (byteRead != -1) {
                //切换到读模式
                buffer.flip();
                while (buffer.hasRemaining()) {
                    System.out.println((char) buffer.get());
                }
                buffer.clear();
                byteRead = socketChannel.read(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
