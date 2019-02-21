package com.kaye.socketdemo.netty02;

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
import io.netty.handler.codec.FixedLengthFrameDecoder;
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

    public static void main(String[] args) throws Exception {
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(worker)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //1.start指定分割符的方式解决拆包粘包
                            // 当遇到 $_时就当成一次数据的请求 要求必须转换成buffer类型的
                            ByteBuf buf = Unpooled.copiedBuffer("$_".getBytes());
                            socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(
                                    //这个参数是指定特殊分割符的最大字节
                                    1024,
                                    //这个参数是指定特殊分割符
                                    buf));
                            //1.end
                            //2.使用消息定长来解决拆包粘包问题 如果长度不够需要用空格填充否则不会发送
                            //建议使用特殊字符的方式来拆包粘包
                            //socketChannel.pipeline().addLast(new FixedLengthFrameDecoder(10))
                            //指定字符串解码格式，这里指定的是字符串类型的解码
                            //设置了这个在handler里就不用把msg转换成ByteBuf再转换成String了
                            //这样设置handler里直接收到的就是String
                            socketChannel.pipeline().addLast(new StringDecoder());
                            //指定字符串编码格式
                            //发送的时候也不用必须是写Buffer了可以写字符串
                            socketChannel.pipeline().addLast(new StringEncoder());
                            //读超时设置
                            socketChannel.pipeline().addLast(new ReadTimeoutHandler(5));
                            socketChannel.pipeline().addLast(new DiscardClientHandler());
                        }
                    });
            ChannelFuture f = b.connect("127.0.0.1", 9998).sync();
            //因为设置了编码所以可以直接写字符串
            f.channel().writeAndFlush("client-1$_");
            f.channel().writeAndFlush("client-2$_");
            f.channel().writeAndFlush("client-3$_");
            f.channel().writeAndFlush("client-4$_");
            f.channel().closeFuture().sync();
        } finally {
            worker.shutdownGracefully();
        }

    }


}
