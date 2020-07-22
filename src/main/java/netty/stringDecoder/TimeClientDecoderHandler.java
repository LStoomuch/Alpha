package netty.stringDecoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.logging.Logger;

public class TimeClientDecoderHandler extends ChannelHandlerAdapter {

    private static final Logger logger = Logger.getLogger(TimeClientDecoderHandler.class.getName());

    private byte[] req;
    private int count;

    public TimeClientDecoderHandler() {
        req = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf message = null;
        for (int i = 0; i < 100; i++) {
            message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;

        System.out.println("现在时间是：" + body + " count is:" + ++count);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warning("exception from downStream:" + cause.getMessage());
        ctx.close();
    }
}
