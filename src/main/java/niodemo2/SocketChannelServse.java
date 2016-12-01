package niodemo2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yance on 2016/11/3.
 */
public class SocketChannelServse implements Runnable {
    private static Selector selector;
    private Map<Integer,SelectionKey> SelectionKeyMap=new ConcurrentHashMap<>();

    public static void main(String[] args) {
        ServerSocketChannel channel = null;
        try {
            channel = ServerSocketChannel.open();
            channel.bind(new InetSocketAddress(9999));
            channel.configureBlocking(false);
            selector = Selector.open();
            channel.register(selector, SelectionKey.OP_ACCEPT);
            new Thread(new SocketChannelServse()).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (selector.select() == 0) {
                    continue;
                }
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isAcceptable()) {
                        ServerSocketChannel socketChannel = (ServerSocketChannel) key.channel();
                        SocketChannel client = socketChannel.accept();
                        if (client != null) {
                            InetSocketAddress address = (InetSocketAddress) client.getLocalAddress();
                            System.out.println("连接成功:" + address.getAddress().getHostAddress());
                            client.configureBlocking(false);
                            client.register(selector, SelectionKey.OP_READ);
                        }
                    } else if (key.isReadable()) {
                        //System.out.println("读就绪");
                        //new Thread(new ProcessThead((SocketChannel)key.channel(),selector)).start();
                        new ProcessThead((SocketChannel)key.channel(),selector).run();
                    }else if (key.isWritable()){
                        //System.out.println("写就绪");
                        SocketChannel channel1= (SocketChannel) key.channel();
                        channel1.register(selector,SelectionKey.OP_READ);
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
