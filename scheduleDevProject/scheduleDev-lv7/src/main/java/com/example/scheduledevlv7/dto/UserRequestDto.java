package com.example.scheduledevlv7.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class UserRequestDto {

    @NotBlank(message = "유저명은 필수입니다.")
    @Size(min = 2, max = 4, message = "유저명은 2글자 이상 정확히 4글자 이하여야 합니다.")
    private  String username;

    @NotBlank(message = "이메일은 필수입니다.")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "올바른 이메일 형식을 입력해주세요.")
    private  String email;

}
