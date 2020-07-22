package netty.stringDecoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.Date;

public class TimeServerDecoderHandler extends ChannelHandlerAdapter {
    private int count = 0;
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;

        System.out.println("时间服务器收到data：" + body + " count is " + ++count);

        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
        currentTime = currentTime + System.getProperty("line.separator");
        ByteBuf res = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.writeAndFlush(res);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
