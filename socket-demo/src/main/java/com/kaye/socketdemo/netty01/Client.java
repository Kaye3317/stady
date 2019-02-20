package com.kaye.socketdemo.netty01;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * netty client端
 *
 * @author yk
 * @since 2019/2/19$ 11:00$
 */
public class Client {

    public static void main(String[] args) throws Exception {
        //client只需要一个线程组  因为它不需要接收
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            //client用的是Bootstrap,server用的是ServerBootstrap
            Bootstrap b = new Bootstrap();
            b.group(worker)
                    //由于演示的是TCP所以客户端使用的是NioSocketChannel
                    .channel(NioSocketChannel.class)
                    //server端用的childHandler但是client用的是handler
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //载入我们自己的ClientHandler 可以实例化多个处理器
                            socketChannel.pipeline().addLast(new DiscardClientHandler());
                        }
                    });
            //为Future，是因为获取到的是异步的通道
            ChannelFuture f = b.connect("127.0.0.1", 9998).sync();

            //ChannelFuture需要去获取它的通道才能写数据,只能写buffer类型的数据，可以通过适配器传对象或者字符串；
            f.channel().write(Unpooled.copiedBuffer("444".getBytes()));
            // f.channel().write(Unpooled.copiedBuffer("555".getBytes()));
            // f.channel().write(Unpooled.copiedBuffer("666".getBytes()));
            f.channel().flush();
            /*
            Thread.sleep(3000);
            //获取到通道去传数据
            //Unpooled是nio辅助工具类 返回的都是一个buffer
            f.channel().writeAndFlush(Unpooled.copiedBuffer("777".getBytes()));
            f.channel().writeAndFlush(Unpooled.copiedBuffer("888".getBytes()));

            Thread.sleep(2000);
            f.channel().writeAndFlush(Unpooled.copiedBuffer("999".getBytes()));*/

            f.channel().closeFuture().sync();
        } finally {
            worker.shutdownGracefully();
        }

    }


}
