package netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class NettyServer {

    public void bind(int port) throws Exception {
        //配置线程组
        //NioEventLoopGroup实际上是Reactor线程组，负责调度和执行客户端的接入、网络读写事件
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            //启动辅助类
            ServerBootstrap bootstrap = new ServerBootstrap();
            //绑定Reactor线程池
            bootstrap.group(bossGroup, workGroup)
                    //绑定服务端Channel
                    .channel(NioServerSocketChannel.class)
                    //网络事件责任链（非服务端必须设置）
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    //用户定制和扩展，利用ChannelHandler可以完成消息编解码、心跳、安全认证、流量控制等等
                    .childHandler(new ChildChannelHandler());
            //绑定端口、同步等待成功
            ChannelFuture future = bootstrap.bind(port).sync();
            //等待服务端监听端口关闭
            future.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
//            socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
//            socketChannel.pipeline().addLast(new StringDecoder());
            socketChannel.pipeline().addLast(new TimeServerHandler());
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        new NettyServer().bind(port);
    }

}
