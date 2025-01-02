package com.example.scheduledevtesterlv1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * ScheduleDevLv8Application 클래스
 * Spring Boot 애플리케이션의 진입점.
 */
@EnableJpaAuditing  // JPA 감사 기능 활성화 (생성일시, 수정일시 등을 자동으로 처리)
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})  // Spring Boot 애플리케이션 설정, 기본 보안 설정을 제외
public class ScheduleDevTesterLv1Application {

    /**
     * 애플리케이션의 main 메서드
     * @param args 실행 시 전달된 인자
     */
    public static void main(String[] args) {
        // Spring Boot 애플리케이션 실행
        SpringApplication.run(ScheduleDevTesterLv1Application.class, args);
    }
}