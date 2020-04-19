package com.mud.game;

import com.mud.game.server.ServerManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MudGameServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MudGameServerApplication.class, args);
        ServerManager.start();
    }

}
