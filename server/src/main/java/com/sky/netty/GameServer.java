package com.sky.netty;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class GameServer {

    @Resource
    private GameServerConfig config;
    @Resource
    private GameServerChanelHandler gameServerChanelHandler;

    private final static Logger logger = LogManager.getLogger();


    public void run() throws Exception {
        EventLoopGroup bossGroup, workerGroup;
        ServerBootstrap bootstrap;

        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();

        bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, config.getBacklog())
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(gameServerChanelHandler);

        try {
            // 绑定端口，同步等待成功
            ChannelFuture future = bootstrap.bind(config.getPort()).sync();
            if (future.isSuccess()) {
                logger.info("server starts success ad port: {}", config.getPort());
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
