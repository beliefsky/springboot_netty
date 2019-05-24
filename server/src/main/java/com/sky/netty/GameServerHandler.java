package com.sky.netty;

import com.sky.demo.IBaseService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@ChannelHandler.Sharable
public class GameServerHandler extends ChannelInboundHandlerAdapter {

    @Resource
    private IBaseService baseService;


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("客户端： " + ctx.channel().id() + "加入连接");
        ctx.channel().writeAndFlush("hi, this is server");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("客户端： " + ctx.channel().id() + "已经离线");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("服务器收到：[" + ctx.channel().id() + "]" + msg);
        baseService.test();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("服务器内部发生错误");
        cause.printStackTrace();
    }
}
