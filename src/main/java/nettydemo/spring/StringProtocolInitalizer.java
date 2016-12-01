package nettydemo.spring;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by yance on 2016/11/7.
 */
@Component
public class StringProtocolInitalizer extends ChannelInitializer<SocketChannel> {
    @Autowired
    private JsonMsgDecoder stringDecoder;
    @Autowired
    private StringEncoder stringEncoder;
    @Autowired
    private ServerHandler serverHandler;


    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline channelPipeline=channel.pipeline();
        channelPipeline.addLast("decoder",stringDecoder);
        channelPipeline.addLast("handler",serverHandler);
        channelPipeline.addLast("encoder",stringEncoder);
    }

    public JsonMsgDecoder getStringDecoder() {
        return stringDecoder;
    }

    public void setStringDecoder(JsonMsgDecoder stringDecoder) {
        this.stringDecoder = stringDecoder;
    }

    public StringEncoder getStringEncoder() {
        return stringEncoder;
    }

    public void setStringEncoder(StringEncoder stringEncoder) {
        this.stringEncoder = stringEncoder;
    }

    public ServerHandler getServerHandler() {
        return serverHandler;
    }

    public void setServerHandler(ServerHandler serverHandler) {
        this.serverHandler = serverHandler;
    }
}
