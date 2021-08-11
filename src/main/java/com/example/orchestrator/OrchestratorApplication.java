package com.example.orchestrator;


import com.example.orchestrator.config.ApplicationProperties;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.io.IOException;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationProperties.class})
public class OrchestratorApplication {


    public static void main(String[] args) throws IOException {

        SpringApplication.run(OrchestratorApplication.class, args);
    }

}
