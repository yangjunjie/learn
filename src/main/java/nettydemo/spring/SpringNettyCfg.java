package nettydemo.spring;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by yance on 2016/11/7.
 */
@Configuration
@ComponentScan
@PropertySource("classpath:springNetty.properties")
public class SpringNettyCfg {
    private int bossThreadCount = 1;
    private int workThreadCount = Runtime.getRuntime().availableProcessors();
    @Value("${tcp.prot}")
    private Integer port;
    private boolean keepAlive = true;
    private int soBackLog = 128;
    @Autowired
    private StringProtocolInitalizer stringProtocolInitalizer;

    private Logger log= LoggerFactory.getLogger(SpringNettyCfg.class);

    @Bean
    public ServerBootstrap bootstrap() {
        ServerBootstrap b = new ServerBootstrap();
        b.group(boosGroup(), workGroup()).channel(NioServerSocketChannel.class).childHandler(stringProtocolInitalizer);
        Map<ChannelOption<?>, Object> tcpChannelOptions = tcpChannelOptions();
        Set<ChannelOption<?>> keySet = tcpChannelOptions.keySet();
        for (ChannelOption option : keySet) {
            b.option(option, tcpChannelOptions.get(option));
        }
        log.debug("bootstrap init finsh");
        return b;
    }


    private NioEventLoopGroup boosGroup() {
        return new NioEventLoopGroup(bossThreadCount);
    }

    private NioEventLoopGroup workGroup() {
        return new NioEventLoopGroup(workThreadCount);
    }

    @Bean
    public InetSocketAddress tcpPort() {
        return new InetSocketAddress(port);
    }

    private Map<ChannelOption<?>, Object> tcpChannelOptions() {
        Map<ChannelOption<?>, Object> options = new HashMap<>();
        options.put(ChannelOption.SO_KEEPALIVE, keepAlive);
        options.put(ChannelOption.SO_BACKLOG, soBackLog);
        return options;
    }

    @Bean
    public JsonMsgDecoder stringDecoder() {
        return new JsonMsgDecoder();
    }

    @Bean
    public StringEncoder stringEncoder() {
        return new StringEncoder();
    }

    @Bean
    public Bootstrap clientBootstrap(){
        Bootstrap cb=new Bootstrap();
        cb.group(workGroup()).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {

            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new ClientHandler());
            }
        }).option(ChannelOption.SO_KEEPALIVE,true);
        return  cb;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
