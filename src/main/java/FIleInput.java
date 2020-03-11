import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FIleInput {
    public static void main(String[] args) {
        try {
            RandomAccessFile aFile = new RandomAccessFile("/Users/sheng/IdeaProject/Alpha/src/main/resources/data/nio-data.txt", "rw");
            FileChannel fileChannel = aFile.getChannel();

            ByteBuffer buf = ByteBuffer.allocate(48);
            int read = fileChannel.read(buf);

            while (read != -1) {
                //flip方法，写模式切换到读模式
                buf.flip();
                while (buf.hasRemaining()) {
                    System.out.println((char) buf.get());
                }
                //切换到写模式
                buf.clear();
                read = fileChannel.read(buf);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
