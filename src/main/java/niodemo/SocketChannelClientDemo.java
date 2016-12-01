package niodemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**nio 消息客户端
 * Created by yance on 2016/11/3.
 */
public class SocketChannelClientDemo {
    public static void main(String[] args) {
        try {
            //打开通道
            SocketChannel channel=SocketChannel.open();
            //设置为非阻塞模式
            channel.configureBlocking(false);
            //连接服务端
            channel.connect(new InetSocketAddress("127.0.0.1",9999));
            //连接完成开始发送数据
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
