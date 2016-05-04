package socketdemo;

import java.io.*;
import java.net.*;

/**
 * socket服务端类
 */
public class SocketServerDemo {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket();

            InetSocketAddress inetAddress = new InetSocketAddress( InetAddress.getLocalHost(), 8899 );
            serverSocket.bind( inetAddress );
            //socket服务端是可以处理多个客户端请求的
            while (true) {
                //accept方法是阻塞会一直等待客户端消息直到接到消息，可以设置超时
                Socket socket = serverSocket.accept();
                //加入多线程异步处理客户端发送的消息
                new Thread( new domgs( socket ) ).start();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            assert serverSocket != null;
            serverSocket.close();
        }

    }

    static class domgs implements Runnable {
        private Socket socket;

        domgs(Socket serverSocket) {
            this.socket = serverSocket;
        }

        public void run() {
            try {
                Reader reader = new InputStreamReader( socket.getInputStream() );
                char chars[] = new char[64];
                int len;
                StringBuilder sb = new StringBuilder();
                while ((len = reader.read( chars )) != -1) {
                    String temp = new String( chars, 0, len );
                    sb.append( temp );
                    if (temp.contains( "end" )) {
                        break;
                    }
                }
                System.out.println( "form client:" + sb );
                Writer writer = new OutputStreamWriter( socket.getOutputStream() );
                writer.write( "来自服务端的回信end" );
                writer.flush();
                writer.close();
                reader.close();
                //socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
