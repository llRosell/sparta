package com.example.scheduledevlv8.controller;

import com.example.scheduledevlv8.dto.UserRequestDto;
import com.example.scheduledevlv8.dto.UserResponseDto;
import com.example.scheduledevlv8.entity.User;
import com.example.scheduledevlv8.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    // 유저 생성
    @PostMapping
    public ResponseEntity<UserResponseDto> saveUser(@Valid  @RequestBody UserRequestDto requestDto) {
        try {
            User savedUser = userService.saveUser(requestDto);
            UserResponseDto responseDto = new UserResponseDto(savedUser);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        } catch (Exception e) {
            // 예외 처리
            e.printStackTrace(); // 로그에 예외를 출력
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 특정 유저 조회
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable("id") Long id) {
        UserResponseDto userResponseDto = userService.findUserById(id);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    // 유저 수정
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable("id") Long id,
            @Valid @RequestBody UserRequestDto dto
    ) {
        UserResponseDto updatedUser = userService.updateUser(id, dto);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    // 유저 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
