package com.example.scheduledevtesterlv2.config;

import com.example.scheduledevtesterlv2.filter.LoginFilter;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebConfig 클래스는 웹 애플리케이션에 대한 설정을 담당합니다.
 * 주로 필터를 등록하거나, MVC 관련 설정을 구성할 때 사용됩니다.
 */
@Configuration // 이 클래스가 스프링의 설정 클래스를 나타낸다는 것을 명시
public class WebConfig implements WebMvcConfigurer {

    /**
     * LoginFilter를 필터 체인에 등록하는 메서드입니다.
     * 이 필터는 HTTP 요청에 대해 로그인 검증을 수행합니다.
     *
     * @return 등록된 FilterRegistrationBean 객체
     */
    @Bean
    public FilterRegistrationBean loginFilter() {
        // FilterRegistrationBean을 생성하고, 로그인 필터를 등록
        FilterRegistrationBean<Filter> filterRegistrationBean =new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }
}
