package niodemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**nio 消息服务端
 * Created by yance on 2016/11/3.
 */
public class SocketChannelServse {
    public static void main(String[] args) {
        ServerSocketChannel channel = null;
        try {
            //打开服务
            channel= ServerSocketChannel.open();
            //监听端口
            channel.bind(new InetSocketAddress(9999));
            //设置为非阻塞模式
            channel.configureBlocking(false);
            //Selector selector = Selector.open();
            //channel.register(selector, SelectionKey.OP_ACCEPT);
            //循环接收客户端连接。
            while (true) {
                //在非阻塞模式下该方法会立即返回，如果没有连接返回null
                SocketChannel client=channel.accept();
                if (client!=null){
                    InetSocketAddress address= (InetSocketAddress) client.getLocalAddress();
                    System.out.println("连接成功"+address.getAddress().getHostAddress());
                    ByteBuffer buffer=ByteBuffer.allocate(64);
                   int len=client.read(buffer);
                    while (len!=-1){
                        System.out.println(len);
                        buffer.flip();
                        while (buffer.hasRemaining()){
                            System.out.print((char) buffer.get());
                        }
                        buffer.clear();
                        len=client.read(buffer);
                    }
                }

                //int readychannl = selector.select();
                /*if (readychannl == 0) {
                    continue;
                }
                Set<SelectionKey> setlkeys = selector.selectedKeys();*/
               /* for (SelectionKey key : setlkeys) {
                    if (key.isAcceptable()){
                     ServerSocketChannel socketChannel= (ServerSocketChannel) key.channel();
                      SocketChannel clien= socketChannel.accept();
                     InetSocketAddress address= (InetSocketAddress) clien.getLocalAddress();
                        System.out.println("连接成功"+address.getAddress().getHostAddress());
                    }
                    setlkeys.remove(key);
                }*/
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
