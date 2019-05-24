package com.sky.demo;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class GameClientChanelHandler extends ChannelInitializer<SocketChannel> {

    @Resource
    private GameClientHandler gameClientHandler;


    @Override
    protected void initChannel(SocketChannel socketChannel) {
        socketChannel.pipeline()
                .addLast("ping", new IdleStateHandler(30, 30,30, TimeUnit.SECONDS))
                .addLast(new StringEncoder(CharsetUtil.UTF_8))
                .addLast(new StringDecoder(CharsetUtil.UTF_8))
                .addLast(gameClientHandler);
    }
}
