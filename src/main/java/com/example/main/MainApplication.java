package com.example.main;


import com.example.main.config.ApplicationProperties;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.io.IOException;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationProperties.class})
public class MainApplication {


    public static void main(String[] args) throws IOException {

        SpringApplication.run(MainApplication.class, args);
    }

}
