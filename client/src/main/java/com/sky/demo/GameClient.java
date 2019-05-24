package com.sky.demo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class GameClient {

    private long restTime = 1000L;

    private Channel channel;


    @Resource
    private GameClientChanelHandler gameClientChanelHandler;

    public void run(String ip, int port) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .handler(gameClientChanelHandler);

        try {
            channel = bootstrap.connect(ip, port).sync().channel();
            loop();
        } catch (Exception e) {
            try {
                Thread.sleep(restTime);
                restTime <<= 1;
            }catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            System.out.println("==========client error============");
            e.printStackTrace();
            System.out.println("==========client replace connection=========");

        }
    }

    private void loop() throws IOException {

        while (channel.isActive()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("请输入：");
            String content = reader.readLine();

            channel.writeAndFlush(content);
//            childChanelHandler.getSocketChannel().writeAndFlush(content);
            System.out.println("发送：" + content);
        }
    }
}
