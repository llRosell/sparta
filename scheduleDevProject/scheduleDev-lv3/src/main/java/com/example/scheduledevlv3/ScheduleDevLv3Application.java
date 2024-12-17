package com.example.scheduledevlv3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ScheduleDevLv3Application {

    public static void main(String[] args) {
        SpringApplication.run(ScheduleDevLv3Application.class, args);
    }

}
