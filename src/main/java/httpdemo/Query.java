package httpdemo;

import org.asynchttpclient.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by yance on 2016/11/29.
 */
public class Query implements  Runnable {
    private AsyncHttpClient asyncHttpClient;
    private Logger log= LoggerFactory.getLogger(Query.class);

    public Query(AsyncHttpClient asyncHttpClient) {
        this.asyncHttpClient = asyncHttpClient;
    }

    @Override
    public void run() {
        log.debug("theard start");

            log.debug("running request");
            Response a = null;
            Future<Response> f = asyncHttpClient.prepareGet("https://www.baidu.com").execute();
            try {
                a = f.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            log.debug("response  status code :{},body:{} ", a.getStatusCode(), a.getResponseBody());

    }
}
