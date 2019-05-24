package com.sky;

import com.sky.netty.GameClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class GameClientApplication implements CommandLineRunner {

    @Resource
    private GameClient  gameClient;

    public static void main(String[] args) {
        SpringApplication.run(GameClientApplication.class);
    }

    @Override
    public void run(String... args) throws Exception {

        gameClient.run("localhost", 8080);
    }
}
