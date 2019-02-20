package com.kaye.socketdemo.netty03;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * java类作用描述
 *
 * @author yk
 * @since 2019/2/20$ 15:41$
 */
public class ServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.err.println("server连接成功");
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Request request = (Request) msg;
        System.err.println(request.toString());
        System.err.println(13213);

        Response response = new Response();
        response.setNum(5569);
        response.setName("server1");
        response.setId(22);

        ctx.writeAndFlush(response);
        System.err.println(132465);


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
