package examples;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class ChannelExamples {

    static void channelRead() throws IOException {
        FileChannel channel = new RandomAccessFile(
                "1.txt", "rw"
        ).getChannel();
        channel.write(ByteBuffer.wrap("Hello world".getBytes()), channel.size());
        ByteBuffer buffer = ByteBuffer.allocate(10);
        while (channel.read(buffer) > 0) {
            buffer.flip();
            while (buffer.hasRemaining()) {
                System.out.print((char) buffer.get());
            }
            buffer.rewind();
            System.out.println();
        }
    }

    public static void main(String[] args) throws IOException {
//        channelRead();

        ByteBuffer buffer = ByteBuffer.allocate(50);

//        buffer.put((byte) 1);
//        buffer.put((byte) 2);
//        buffer.flip();
//        for (int i = 0; i < 2; i++) {
//            System.out.println(buffer.getIn());
//        }
//        buffer.clear();
//        buffer.put((byte) 1);
//        buffer.put((byte) 2);
//        buffer.put((byte) 3);
//        buffer.flip();
//        for (; buffer.hasRemaining(); ) {
//            System.out.println(buffer.get());
//        }
        buffer.put("Привет мир ха ха ха".getBytes(StandardCharsets.UTF_8));
        buffer.flip();
        byte[] buf = new byte[50];
        buffer.get(buf, 0, buffer.limit());
        System.out.println(new String(buf));
    }


}
