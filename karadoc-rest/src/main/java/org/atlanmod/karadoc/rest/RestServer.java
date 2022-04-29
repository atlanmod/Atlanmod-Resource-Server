package org.atlanmod.karadoc.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.emfjson.jackson.module.EMFModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@SpringBootApplication
public class RestServer {


    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        return EMFModule.setupDefaultMapper();
    }


    public static void main(String[] args) {
        SpringApplication.run(RestServer.class, args);
    }

}
