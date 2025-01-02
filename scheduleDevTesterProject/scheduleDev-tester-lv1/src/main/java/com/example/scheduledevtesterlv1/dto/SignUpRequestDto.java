package com.example.scheduledevtesterlv1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class SignUpRequestDto {

    // 이메일은 필수 입력값이며, 형식이 맞는지 확인하는 정규식 패턴 검증
    @NotBlank(message = "이메일은 필수입니다.")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "올바른 이메일 형식을 입력해주세요.")
    private String email;

    // 비밀번호는 필수 입력값으로, 사용자가 계정을 생성할 때 필요한 비밀번호
    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;

}
