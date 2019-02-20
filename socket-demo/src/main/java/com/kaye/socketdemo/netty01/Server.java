package com.kaye.socketdemo.netty01;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * netty server端
 *
 * @author yk
 * @since 2019/2/19$ 11:00$
 */
public class Server {

    public static void main(String[] args) throws Exception {
        //1.NioEventLoopGroup是针对TCP协议的；
        //是用来处理I/O操作的多线程事件循环器
        //bossGroup用来接收client的连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //2.workerGroup用来处理已经被接收的连接,处理实际的业务的
        EventLoopGroup workerGroup = new NioEventLoopGroup();


        try {
            //3.创建辅助类，作用是对server进行一系列的配置
            ServerBootstrap b = new ServerBootstrap();
            //加入两个事件循环器，让‘boss’接收到连接后，把连接信息注册到‘worker’上
            b.group(bossGroup, workerGroup)
                    //指定你是时使用哪一种ServerChannel通道进行连接
                    //由于演示的是TCP所以使用的是NioServerSocketChannel，其他的UDP HTTP可能就是其他通道
                    .channel(NioServerSocketChannel.class)
                    //重要！！！！
                    //一定要使用childHandler去绑定具体是时间处理器
                    //固定写法
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //载入我们自己的ServerHandler，可以实例化多个处理器
                            socketChannel.pipeline().addLast(new DiscardServerHandler());
                        }
                    })
                    //TCP协议配置
                    /**
                     * 服务器端TCP模块维护有2个队列，我们称之为A,B;
                     * 客户端想服务端发起连接Connect的时候，会发送带有SYN标志的包（第一次握手）
                     * 服务端接收到客户端发来的SYN的时候，会向客户端发送SYN ACK进行确认（第二次握手）
                     * 这时，TCP内核模块将客户端连接加入到队列A中，然后服务端收到客户端发来的ACK（第三次握手）时，
                     * TCP内核模块吧哭护短连接从队列A移动到队列B，连接完成，应用程序的accept会返回Socket实例
                     * 也就是说accept从B队列中取出完成三次握手的连接；
                     * A队列和B队列的长度之和就是backlog,当A，B队列的长度之和大于backlog时，
                     * 新链接将会被TCP内核拒绝，所以，如果backlog太小，会出现accept速度跟不上，
                     * A,B队列装满了，导致客户端无法连接，
                     * 请注意，backlog对程序支持的连接数并无影响，backlog影响的只是还没有被accept取出的连接
                     * 所谓还没被accept取出的连接是指还存放在A队列没有移动到B队列的连接。
                     * 生产环境配置为100，默认值是128（作为参考）
                     */
                    //相当于设置TCP连接的缓冲区
                    .option(ChannelOption.SO_BACKLOG, 128)
                    //TCP协议配置
                    //保持连接 默认就是true
                    //.option(ChannelOption.SO_KEEPALIVE, true)指的是当前的server通道
                    //.childOption(ChannelOption.SO_KEEPALIVE, true)指的是以后可能程序复杂了会有的子通道
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    //还有更多配置比如
                    //设置发送缓冲大小
                    .option(ChannelOption.SO_SNDBUF, 32 * 1024)
                    //设置接收缓冲大小
                    .option(ChannelOption.SO_RCVBUF, 32 * 1024);


            //绑定端口进行监听，看有没有客户端连接进来
            //使用.sync()是因为它是异步监听
            ChannelFuture f = b.bind(9998).sync();

            //相当于在main方法中阻塞一下
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
