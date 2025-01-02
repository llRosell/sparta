package com.example.scheduledevtesterlv2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * SecurityConfig는 애플리케이션의 보안 설정을 담당하는 클래스입니다.
 * 여기에서는 비밀번호 암호화 방식으로 BCrypt를 사용하는 설정을 제공합니다.
 */
@Configuration // 이 클래스가 스프링의 설정 클래스를 나타낸다는 것을 명시
public class SecurityConfig {

    /**
     * PasswordEncoder 빈을 생성하여 BCryptPasswordEncoder를 반환합니다.
     * BCrypt는 강력한 비밀번호 암호화 알고리즘으로, 비밀번호를 안전하게 저장하는 데 사용됩니다.
     *
     * @return PasswordEncoder 객체 (BCryptPasswordEncoder)
     */
    @Bean // 이 메서드가 반환하는 객체를 스프링 컨테이너의 빈으로 등록
    public PasswordEncoder passwordEncoder() {
        // BCryptPasswordEncoder를 사용하여 비밀번호를 안전하게 암호화
        return new BCryptPasswordEncoder();
    }
}
