package com.example.scheduledevlv5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ScheduleDevLv5Application {

    public static void main(String[] args) {
        SpringApplication.run(ScheduleDevLv5Application.class, args);
    }

}
