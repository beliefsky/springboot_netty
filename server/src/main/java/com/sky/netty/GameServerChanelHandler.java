package com.sky.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class GameServerChanelHandler extends ChannelInitializer<SocketChannel> {

    @Resource
    private GameServerHandler gameServerHandler;


    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline()
//                .addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()))
                .addLast(new StringEncoder(CharsetUtil.UTF_8))
                .addLast(new StringDecoder(CharsetUtil.UTF_8))
                .addLast(gameServerHandler);
    }
}
