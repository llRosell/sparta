package com.example.scheduledevtesterlv1.controller;

import com.example.scheduledevtesterlv1.dto.UserRequestDto;
import com.example.scheduledevtesterlv1.dto.UserResponseDto;
import com.example.scheduledevtesterlv1.entity.User;
import com.example.scheduledevtesterlv1.service.UserService;
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

    /**
     * 유저 생성 API
     * 새로운 유저를 생성하고, 생성된 유저 정보를 반환합니다.
     * @param requestDto 생성할 유저의 정보
     * @return 생성된 유저의 정보
     */
    @PostMapping
    public ResponseEntity<UserResponseDto> saveUser(@Valid @RequestBody UserRequestDto requestDto) {
        try {
            // 유저 생성 처리
            User savedUser = userService.saveUser(requestDto);
            UserResponseDto responseDto = new UserResponseDto(savedUser);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED); // 201 반환
        } catch (Exception e) {
            // 예외 발생 시 로그에 출력하고 401 반환
            log.error("유저 생성 중 오류 발생: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 특정 유저 조회 API
     * 유저 ID를 통해 특정 유저의 정보를 조회하고 반환합니다.
     * @param id 조회할 유저의 ID
     * @return 유저의 정보
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable("id") Long id) {
        try {
            // 유저 조회 처리
            UserResponseDto userResponseDto = userService.findUserById(id);
            return new ResponseEntity<>(userResponseDto, HttpStatus.OK); // 200 반환
        } catch (Exception e) {
            // 조회 중 예외 발생 시 로그에 출력하고 401 반환
            log.error("유저 조회 중 오류 발생: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 유저 수정 API
     * 유저 ID와 새로운 유저 정보를 받아 해당 유저의 정보를 수정하고, 수정된 유저 정보를 반환합니다.
     * @param id 수정할 유저의 ID
     * @param dto 수정할 유저의 새로운 정보
     * @return 수정된 유저의 정보
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable("id") Long id,
            @Valid @RequestBody UserRequestDto dto
    ) {
        try {
            // 유저 수정 처리
            UserResponseDto updatedUser = userService.updateUser(id, dto);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK); // 200 반환
        } catch (Exception e) {
            // 수정 중 예외 발생 시 로그에 출력하고 401 반환
            log.error("유저 수정 중 오류 발생: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 유저 삭제 API
     * 유저 ID를 통해 해당 유저를 삭제합니다.
     * @param id 삭제할 유저의 ID
     * @return 삭제 완료 상태
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
        try {
            // 유저 삭제 처리
            userService.deleteUser(id);
            return new ResponseEntity<>("유저가 삭제되었습니다.", HttpStatus.OK); // 성공 메시지 반환
        } catch (Exception e) {
            // 삭제 중 예외 발생 시 로그에 출력하고 401 반환
            log.error("유저 삭제 중 오류 발생: {}", e.getMessage());
            return new ResponseEntity<>("유저 삭제 중 문제가 발생했습니다.", HttpStatus.UNAUTHORIZED);
        }
    }
}
