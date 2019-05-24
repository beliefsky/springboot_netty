package com.sky.netty;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class GameServer {

    @Resource
    private GameServerChanelHandler gameServerChanelHandler;


    public void run(int port) throws Exception {
        EventLoopGroup bossGroup, workerGroup;
        ServerBootstrap bootstrap;

        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();

        bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(gameServerChanelHandler);

        try {
            // 绑定端口，同步等待成功
            ChannelFuture future = bootstrap.bind(port).sync();
            if (future.isSuccess()) {
                System.out.println("server starts success ad port:" + port);
            }
            // 等待服务监听端口关闭
            future.channel().closeFuture().sync();
        } finally {
            // 退出，释放线程资源
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
