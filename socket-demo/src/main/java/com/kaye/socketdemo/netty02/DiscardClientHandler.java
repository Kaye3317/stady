package com.kaye.socketdemo.netty02;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

/**
 * client 处理器
 * 处理器的职责是释放所有传递到处理器的引用计数对象
 *
 * @author yk
 * @since 2019/2/19$ 11:50$
 */
public class DiscardClientHandler extends ChannelHandlerAdapter {
    /**
     * 当client端管道联通时可以做的一些操作
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.err.println("client端联通");
        ctx.writeAndFlush("client端联通了，发了一些数据到server端$_");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            //因为设置了解码所以可以直接接受字符串
            String buf = (String) msg;
            System.err.println("client1: " + buf);

        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
