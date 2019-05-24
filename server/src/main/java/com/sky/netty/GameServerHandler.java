package com.sky.netty;

import com.sky.demo.IBaseService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@ChannelHandler.Sharable
public class GameServerHandler extends ChannelInboundHandlerAdapter {

    @Resource
    private IBaseService baseService;

    private final static Logger logger = LogManager.getLogger();


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        logger.info("客户端： {} 加入连接", ctx.channel().id());
        ctx.channel().writeAndFlush("hi, this is server");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        logger.info("客户端： {} 已经离线", ctx.channel().id());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        logger.info("服务器收到：[{}]{}", ctx.channel().id(), msg);
        baseService.test();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.info("服务器内部发生错误");
        cause.printStackTrace();
    }
}
