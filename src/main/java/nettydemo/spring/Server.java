package nettydemo.spring;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;

/**
 * Created by yance on 2016/11/7.
 */
@Component("server")
public class Server {
    @Autowired
    private ServerBootstrap serverBootstrap;
    @Autowired
    private InetSocketAddress tcpprot;
    private ChannelFuture channelFuture;

    @PostConstruct
    public void start() throws InterruptedException {
      channelFuture=serverBootstrap.bind(tcpprot).sync();
    }
    @PreDestroy
    public void stop() throws InterruptedException {
        channelFuture.channel().closeFuture().sync();
    }

    public ServerBootstrap getServerBootstrap() {
        return serverBootstrap;
    }

    public void setServerBootstrap(ServerBootstrap serverBootstrap) {
        this.serverBootstrap = serverBootstrap;
    }

    public InetSocketAddress getTcpprot() {
        return tcpprot;
    }

    public void setTcpprot(InetSocketAddress tcpprot) {
        this.tcpprot = tcpprot;
    }
}
