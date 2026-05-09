package com.mayora.meetingroom;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class MeetingroomApplication {

    public static void main(String[] args) {
        SpringApplication.run(MeetingroomApplication.class, args);
    }

    @Bean
    CommandLineRunner runner() {
        return args -> {
            log.info("=======================================");
            log.info("🚀 MEETING ROOM SYSTEM STARTED");
            log.info("🌐 Server: http://localhost:9000");
            log.info("📘 Swagger: http://localhost:9000/swagger-ui.html");
            log.info("=======================================");
        };
    
	}
	
}