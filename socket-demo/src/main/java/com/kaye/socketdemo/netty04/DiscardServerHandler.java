package com.kaye.socketdemo.netty04;

import com.sun.xml.internal.bind.v2.model.core.ID;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.Map;

/**
 * server 处理器
 * 处理器的职责是释放所有传递到处理器的引用计数对象
 *
 * @author yk
 * @since 2019/2/19$ 11:29$
 */
public class DiscardServerHandler extends ChannelHandlerAdapter {

    /**
     * 模拟数据库里的认证数据
     */
    private static Map<String, String> map;

    static {
        map = new HashMap<>(1);
        map.put("client01", "123456");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.err.println("server端联通");
    }

    /**
     * 模拟认证相关逻辑
     *
     * @param ctx
     */
    public void authentication(ChannelHandlerContext ctx, String msg) {
        String[] arr = msg.split(",");
        if (map.get(arr[0]).equals(arr[1])) {
            ctx.writeAndFlush("ok$_");
        } else {
            //认证失败关闭通道
            ctx.writeAndFlush("认证失败$_").addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.err.println("server收到连接请求");
        //因为设置了解码所以可以直接接受字符串
        String buf = (String) msg;
        String[] arr = buf.split(",");

        if (map.keySet().contains(arr[0])) {
            authentication(ctx, buf);
        } else if ("data".equals(arr[0])) {
            System.err.println("client发来的心跳数据" + buf);
            ctx.writeAndFlush("收到心跳$_");
        } else {
            //认证失败关闭通道
            ctx.writeAndFlush("你是一个异常连接$_").addListener(ChannelFutureListener.CLOSE);
        }


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
