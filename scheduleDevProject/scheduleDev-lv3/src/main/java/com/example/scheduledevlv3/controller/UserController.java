package com.example.scheduledevlv3.controller;

import com.example.scheduledevlv3.dto.UserRequestDto;
import com.example.scheduledevlv3.dto.UserResponseDto;
import com.example.scheduledevlv3.entity.User;
import com.example.scheduledevlv3.service.UserService;
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
        User savedUser = userService.saveUser(requestDto);
        return new ResponseEntity<>(new UserResponseDto(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail()), HttpStatus.CREATED);
    }


    // 특정 유저 조회
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findUserById(
            @PathVariable("id") Long id,
            @RequestParam(name = "password") String password
    ) {
        UserResponseDto userResponseDto = userService.findUserById(id, password);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    // 특정 유저 수정
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable("id") Long id,
            @RequestBody UserRequestDto dto,
            @RequestParam("password") String password
    ) {
        UserResponseDto updatedAuthor = userService.updateUser(id, dto, password);
        return new ResponseEntity<>(updatedAuthor, HttpStatus.OK);
    }

    // 특정 유저 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable("id") Long id,
            @RequestParam("password") String password
    ) {
        userService.deleteUser(id, password);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
