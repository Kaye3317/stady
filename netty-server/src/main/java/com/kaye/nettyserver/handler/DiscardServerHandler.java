package com.kaye.nettyserver.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

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
        //try {
        //一些业务处理逻辑
        ByteBuf buf = (ByteBuf) msg;

        byte[] data = new byte[buf.readableBytes()];
        //把数据从buffer读到data数组
        buf.readBytes(data);
        String s = new String(data, "utf-8");
        System.err.println("server: " + s);

        //给client发送消息
        String response = "888" + s;
        //ChannelHandlerContext直接就可以写不需要获取通道
        //由于write会自动释放msg所以不需要我们手动释放
        //ctx.writeAndFlush() 会返回一个ChannelFuture
        //只能写buffer类型的数据，可以通过适配器传对象或者字符串；
        ctx.writeAndFlush(Unpooled.copiedBuffer(response.getBytes()))
                //客户端响应完了就会主动断开连接，关闭通道一定是server来关闭
                /** writeAndFlush返回一个Future 可以使用Future添加一个监听
                 * 什么时候client接受完了数据就可以关闭client了
                 * 也可以简写 向下面一样
                 final ChannelFuture f = ctx.writeAndFlush(time);
                 f.addListener(new ChannelFutureListener() {
                @Override public void operationComplete(ChannelFuture future) {
                assert f == future;
                ctx.close();
                }
                })
                 */
                //简写方法 用了这个相当于是个短链接，不用相当于是个长连接
                .addListener(ChannelFutureListener.CLOSE);

        //} finally {
        //业务处理完了后一定要释放数据，msg一般是netty里的ByteBuf
        //((ByteBuf) msg).release()
        //ReferenceCountUtil是释放计数对象的工具
        //ReferenceCountUtil.release(msg);
        //}
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
