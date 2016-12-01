package niodemo2;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * Created by yance on 2016/11/3.
 */
public class ProcessThead implements Runnable {
    private SocketChannel client;
    private Selector selector;


    public ProcessThead(SocketChannel client,Selector selector) {
        this.client=client;
        this.selector=selector;
    }

    @Override
    public void run() {
        try {
            readdata();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readdata() throws IOException {
        if (client != null) {
            ByteBuffer buffer = ByteBuffer.allocate(3);
            int len = client.read(buffer);
            if (len==-1){
                client.register(selector, SelectionKey.OP_WRITE);
            }
            while (len != -1) {
                buffer.flip();
                System.out.print(new String(buffer.array(),0,len));
                buffer.clear();
                len = client.read(buffer);
            }
        }

    }
}
