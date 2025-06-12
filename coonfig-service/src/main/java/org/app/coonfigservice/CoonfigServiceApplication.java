package org.app.coonfigservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class CoonfigServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoonfigServiceApplication.class, args);
    }

}
