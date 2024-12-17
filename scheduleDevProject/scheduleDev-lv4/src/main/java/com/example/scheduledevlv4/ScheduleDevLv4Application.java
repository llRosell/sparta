package com.example.scheduledevlv4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ScheduleDevLv4Application {

    public static void main(String[] args) {
        SpringApplication.run(ScheduleDevLv4Application.class, args);
    }

}
