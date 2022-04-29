package org.atlanmod.karadoc.rest;

import org.atlanmod.karadoc.core.ResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestServer {

    private ResourceServer server;

    public static void main(String[] args) {
        SpringApplication.run(RestServer.class, args);
    }

}
