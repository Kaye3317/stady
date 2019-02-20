package com.kaye.socketdemo.netty03;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * java类作用描述
 *
 * @author yk
 * @since 2019/2/20$ 15:40$
 */
public class Server {

    public static void main(String[] args) {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup work = new NioEventLoopGroup();
        ServerBootstrap bootstrapServer = new ServerBootstrap();
        try {
            bootstrapServer.group(boss, work)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //添加序列化解码
                            socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
                            //添加序列化编码
                            socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
                            socketChannel.pipeline().addLast(new ServerHandler());
                        }
                    });
            ChannelFuture future = bootstrapServer.bind(11111).sync();

            future.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }

    }
}
