package com.kaye.socketdemo.netty04;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

import java.util.concurrent.*;

/**
 * client 处理器
 * 处理器的职责是释放所有传递到处理器的引用计数对象
 *
 * @author yk
 * @since 2019/2/19$ 11:50$
 */
public class DiscardClientHandler extends ChannelHandlerAdapter {

    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);


    /**
     * 当client端管道联通时可以做的一些操作
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.err.println("client准备发起连接");
        String key = "client01";
        String psw = "123456";
        ctx.writeAndFlush(key + "," + psw + "$_");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            //因为设置了解码所以可以直接接受字符串
            String buf = (String) msg;
            if ("ok".equals(buf)) {
                executorService.scheduleWithFixedDelay(() ->
                        ctx.writeAndFlush("data,client发起的心跳数据$_"), 0, 5, TimeUnit.SECONDS);
            } else {
                System.err.println(msg);
            }
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
