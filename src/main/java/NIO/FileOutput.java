package NIO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileOutput {
    public static void main(String[] args) {
        FileChannel channel = null;
        try {
            RandomAccessFile aFile = new RandomAccessFile("/Users/sheng/IdeaProject/Alpha/src/main/resources/data/nio-data.txt", "rw");
            channel = aFile.getChannel();

            String newData = "New String to write to file..." + System.currentTimeMillis();

            ByteBuffer buf = ByteBuffer.allocate(48);
            buf.clear();
            buf.put(newData.getBytes());

            buf.flip();

            while(buf.hasRemaining()) {
                channel.write(buf);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
