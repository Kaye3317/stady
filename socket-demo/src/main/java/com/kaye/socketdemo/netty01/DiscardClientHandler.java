package com.kaye.socketdemo.netty01;

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

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            //一些业务处理逻辑
            ByteBuf buf = (ByteBuf) msg;
            byte[] data = new byte[buf.readableBytes()];
            //把数据从buffer读到data数组
            buf.readBytes(data);
            String s = new String(data, "utf-8");
            System.err.println("client: " + s);

        } finally {
            //业务处理完了后一定要释放数据，msg一般是netty里的ByteBuf
            //((ByteBuf) msg).release()
            //ReferenceCountUtil是释放计数对象的工具
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
