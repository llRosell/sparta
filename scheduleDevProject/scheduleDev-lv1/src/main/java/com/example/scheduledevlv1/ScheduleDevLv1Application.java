package com.example.scheduledevlv1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ScheduleDevLv1Application {

    public static void main(String[] args) {
        SpringApplication.run(ScheduleDevLv1Application.class, args);
    }

}
