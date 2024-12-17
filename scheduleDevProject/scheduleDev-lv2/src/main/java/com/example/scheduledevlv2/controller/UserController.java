package com.example.scheduledevlv2.controller;

import com.example.scheduledevlv2.dto.UserRequestDto;
import com.example.scheduledevlv2.dto.UserResponseDto;
import com.example.scheduledevlv2.entity.User;
import com.example.scheduledevlv2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 유저 생성
    @PostMapping
    public ResponseEntity<UserResponseDto> saveUser(@RequestBody UserRequestDto requestDto) {
        // 유저 생성 및 저장
        User savedUser = userService.saveUser(requestDto.getUsername(), requestDto.getEmail());
        return new ResponseEntity<>(new UserResponseDto(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail()), HttpStatus.CREATED);
    }


    // 특정 유저 조회
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable("id") Long id) {
        UserResponseDto userResponseDto = userService.findUserById(id);

        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    // 유저 수정
    @PutMapping("/{id}") // 경로 수정
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable("id") Long id,
            @RequestBody UserRequestDto dto
    ) {
        UserResponseDto updatedUser = userService.updateUser(id, dto); // 수정
        return new ResponseEntity<>(updatedUser, HttpStatus.OK); // 상태 코드 200 반환
    }

    // 특정 유저 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
