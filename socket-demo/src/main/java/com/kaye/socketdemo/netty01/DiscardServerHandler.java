package com.kaye.socketdemo.netty01;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

/**
 * server 处理器
 * 处理器的职责是释放所有传递到处理器的引用计数对象
 *
 * @author yk
 * @since 2019/2/19$ 11:29$
 */
public class DiscardServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        try {
            //一些业务处理逻辑
            ByteBuf buf = (ByteBuf) msg;

            byte[] data = new byte[buf.readableBytes()];
            buf.readBytes(data);
            String s = new String(data, "utf-8");
            System.err.println("server: " + s);

            //给client发送消息
            String response = "888";
            //ChannelHandlerContext直接就可以写不需要获取通道
            //由于write会自动释放msg所以不需要我们手动释放
            //ctx.writeAndFlush() 会返回一个ChannelFuture
            ctx.writeAndFlush(Unpooled.copiedBuffer(response.getBytes()))
                    //客户端响应完了就会主动断开连接
                    .addListener(ChannelFutureListener.CLOSE);

//        } finally {
            //业务处理完了后一定要释放数据，msg一般是netty里的ByteBuf
            //((ByteBuf) msg).release()
            //ReferenceCountUtil是释放计数对象的工具
            //ReferenceCountUtil.release(msg);
//        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
