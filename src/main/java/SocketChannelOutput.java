import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketChannelOutput {
    public static void main(String[] args) {

        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.socket().connect(new InetSocketAddress( 9998));

            String newData = "New String to write to file..." + System.currentTimeMillis();

            ByteBuffer byteBuffer = ByteBuffer.allocate(48);
            byteBuffer.clear();
            byteBuffer.put(newData.getBytes());

            byteBuffer.flip();

            while (byteBuffer.hasRemaining()) {
                socketChannel.write(byteBuffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
