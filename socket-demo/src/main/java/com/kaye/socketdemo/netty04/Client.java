package com.kaye.socketdemo.netty04;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.ReadTimeoutHandler;

/**
 * netty client端
 *
 * @author yk
 * @since 2019/2/19$ 11:00$
 */
public class Client {

    private EventLoopGroup worker;
    private Bootstrap b;
    private ChannelFuture f;


    private Client() {
        worker = new NioEventLoopGroup();
        b = new Bootstrap();
        b.group(worker)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ByteBuf buf = Unpooled.copiedBuffer("$_".getBytes());
                        socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, buf));
                        socketChannel.pipeline().addLast(new StringEncoder());
                        socketChannel.pipeline().addLast(new StringDecoder());
                        //读超时设置
                        //socketChannel.pipeline().addLast(new ReadTimeoutHandler(10));
                        socketChannel.pipeline().addLast(new DiscardClientHandler());
                    }
                });

    }

    private static class Inner {
        private static final Client CLIENT = new Client();

    }

    public static Client getInstance() {
        return Inner.CLIENT;
    }

    public void connect() {
        try {
            f = b.connect("127.0.0.1", 9998).sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ChannelFuture getChannelFuture() {
        if (f == null) {
            connect();
        }
        if (!f.channel().isActive()) {
            connect();
        }
        return f;
    }

    public static void main(String[] args) throws Exception {
        Client client = Client.getInstance();
        client.connect();
        ChannelFuture cf = client.getChannelFuture();
        cf.channel().closeFuture().sync();
    }


}
