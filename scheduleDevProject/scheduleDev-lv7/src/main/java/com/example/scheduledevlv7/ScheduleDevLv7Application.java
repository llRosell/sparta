package com.example.scheduledevlv7;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class ScheduleDevLv7Application {
    public static void main(String[] args) {
        SpringApplication.run(ScheduleDevLv7Application.class, args);
    }
}