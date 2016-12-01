package niodemo;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by yance on 2016/11/2.
 */
public class FileNioDemo {

    public static void main(String[] args) throws FileNotFoundException {
        RandomAccessFile file=new RandomAccessFile(":nio.txt","rw");
        FileChannel channe= file.getChannel();
        ByteBuffer buffer=ByteBuffer.allocate(48);
        try {
            int len= channe.read(buffer);
            while (len!=-1){
                System.out.println("Read " + len);
                buffer.flip();
                while (buffer.hasRemaining()){
                    System.out.print((char) buffer.get());
                }
                buffer.clear();
                len=channe.read(buffer);
            }
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
