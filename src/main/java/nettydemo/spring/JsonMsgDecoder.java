package nettydemo.spring;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * Created by yance on 2016/11/8.
 */
public class JsonMsgDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        msg.markReaderIndex();
        int len=msg.readableBytes();
        if (len<23){
            return;
        }
        byte[] header=new byte[6];
        msg.readBytes(header);
       String headStr=new String(header,"UTF-8");
        if (!headStr.equals("#XRPC#")){
                return;
        }
        if (len<msg.getShort(8)){
            msg.resetReaderIndex();
            return;
        }
        byte [] b=new byte[len-6];
        msg.readBytes(b);
        Message ms=new Message();
        ms.setMainVersion(msg.readByte());
        ms.setSubVersion(msg.readByte());
        ms.setMsgLen(msg.readShort());
        ms.setCommandID(msg.readShort());
        ms.setSequeeceNo(msg.readInt());
        ms.setEncType(msg.readByte());
        ms.setCookieLen(msg.readShort());
        ms.setReserve(msg.readInt());
        byte[] cookie = new byte[ms.getCookieLen()];
        msg.readBytes(cookie);
        ms.setCookie(new String(cookie, "UTF-8"));
        // 获取包体
        byte[] body = new byte[ms.getMsgLen() - ms.getCookieLen()];
        msg.readBytes(body);
        ms.setJson(JSONObject.parseObject(new String(body, "UTF-8")));
        out.add(ms);
    }
}
