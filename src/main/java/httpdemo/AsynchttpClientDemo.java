package httpdemo;

import org.asynchttpclient.AsyncHttpClientConfig;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by yance on 2016/11/23.
 */
public class AsynchttpClientDemo {
    private static Logger log = LoggerFactory.getLogger(AsynchttpClientDemo.class);

    public static void main(String[] args){
        AsyncHttpClientConfig config = new DefaultAsyncHttpClientConfig.Builder().setPooledConnectionIdleTimeout(1000).build();
        DefaultAsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient(config);
        ExecutorService executorService= Executors.newCachedThreadPool();
        executorService.execute(new Query(asyncHttpClient));
    }
}
