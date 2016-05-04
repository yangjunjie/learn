import java.io.*;
import java.net.*;

/**
 * Created by thinkpad on 2016/5/3.
 */
public class SocketServerDemo {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket();

            InetSocketAddress inetAddress = new InetSocketAddress( InetAddress.getLocalHost(), 8899 );
            serverSocket.bind( inetAddress );
            while (true) {
                Socket socket = serverSocket.accept();
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
