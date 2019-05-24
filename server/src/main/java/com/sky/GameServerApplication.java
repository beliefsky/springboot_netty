package com.sky;

import com.sky.netty.GameServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class GameServerApplication implements CommandLineRunner {

    @Resource
    private GameServer gameServer;

    public static void main(String[] args) {
        SpringApplication.run(GameServerApplication.class);
    }

    @Override
    public void run(String... args) throws Exception {

        gameServer.run(8080);
    }
}