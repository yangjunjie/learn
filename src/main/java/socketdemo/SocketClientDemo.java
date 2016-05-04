package socketdemo;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * socket客户端类
 */
public class SocketClientDemo {
    public static void main(String[] args) throws IOException {
            Thread thread = new Thread( new Runnable() {
                public void run() {
                    Reader reader=null;
                    Writer writer=null;
                    Socket socket=null;
                    try {
                        //模拟无限个客户端
                        while (true) {
                            socket = new Socket( InetAddress.getLocalHost(), 8899 );
                            writer = new OutputStreamWriter( socket.getOutputStream() );
                            writer.write( "你好，我是client" + Thread.currentThread().getName() );
                            //writer 没close 、异常时，read方法会一直等待，防止相互等待加入结束标志
                            writer.write( "end" );
                            writer.flush();
                            reader = new InputStreamReader( socket.getInputStream() );
                            char chars[] = new char[64];
                            StringBuilder sb = new StringBuilder();
                            int len;
                            while ((len = reader.read( chars )) != -1) {
                                String temp = new String( chars, 0, len );
                                sb.append( temp );
                                //遇到结束标记退出循环
                                if (temp.contains( "end" ))
                                    break;
                            }
                            System.out.println( Thread.currentThread().getName() + "form server:" + sb );
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (reader!=null)
                            reader.close();
                            if (writer!=null)
                            writer.close();
                            if (socket!=null)
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } );
            thread.start();


    }

}
