package com.kaye.socketdemo.netty03;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

/**
 * java类作用描述
 *
 * @author yk
 * @since 2019/2/20$ 15:40$
 */
public class Client {

    public static void main(String[] args) {
        EventLoopGroup worker = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(worker)
                    .channel(NioSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //添加序列化解码
                            socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
                            //添加序列化编码
                            socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
                            socketChannel.pipeline().addLast(new ReadTimeoutHandler(5));
                            socketChannel.pipeline().addLast(new ClientHandler());
                        }
                    });
            ChannelFuture future = bootstrap.connect("127.0.0.1", 11111).sync();

            for (int i = 0; i < 3; i++) {
                Request request = new Request();
                request.setId(i);
                request.setName("client--" + i);
                request.setNum(i);
                future.channel().writeAndFlush(request);
            }

            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            worker.shutdownGracefully();
        }

    }
}
