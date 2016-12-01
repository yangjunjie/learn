package nettydemo.spring;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;

/**
 * Created by yance on 2016/11/11.
 */
@Component
@DependsOn("server")
public class Client {
    @Autowired
    private Bootstrap c;
   /* @Autowired
    private InetSocketAddress tcpprot;*/
    private ChannelFuture channelFuture;
    @PostConstruct
    public void start(){

                try {
                    channelFuture=c.connect(new InetSocketAddress("127.0.0.1",9999)).sync();
                } catch (Exception e) {
                    e.printStackTrace();
                }
    }
    @PreDestroy
    public void  stop() throws InterruptedException {
        channelFuture.channel().closeFuture().sync();
    }

}
