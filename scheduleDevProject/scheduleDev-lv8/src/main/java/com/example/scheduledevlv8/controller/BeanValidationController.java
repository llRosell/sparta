package com.example.scheduledevlv8.controller;

import com.example.scheduledevlv8.dto.SignUpRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class BeanValidationController {

    @PostMapping("/error-message")
    public String beanValidation(
            @Validated @RequestBody SignUpRequestDto dto,
            BindingResult bindingResult
    ) {
        // bindingResult Field Error 출력
        if (bindingResult.hasErrors()) {
            return String.valueOf(bindingResult.getFieldError());
        }

        // 성공시 문자열 반환
        return "회원가입 성공";
    }

}