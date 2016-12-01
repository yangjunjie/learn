package niodemo2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by yance on 2016/11/3.
 */
public class SocketChannelClientDemo {
    public static void main(String[] args) {
        try {
            SocketChannel channel=SocketChannel.open();
            channel.configureBlocking(false);
            channel.connect(new InetSocketAddress("127.0.0.1",9999));
            if(channel.finishConnect()) {
                ByteBuffer buf = ByteBuffer.allocate(48);
                buf.clear();
                buf.put("hello word nio".getBytes());
                buf.flip();
                while (buf.hasRemaining()){
                    channel.write(buf);
                }
                channel.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
