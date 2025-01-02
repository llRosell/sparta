package com.example.scheduledevtesterlv1.service;

import com.example.scheduledevtesterlv1.dto.UserRequestDto;
import com.example.scheduledevtesterlv1.dto.UserResponseDto;
import com.example.scheduledevtesterlv1.entity.User;
import com.example.scheduledevtesterlv1.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 새로운 유저 생성
     * @param dto 유저 생성 요청 DTO
     * @return 생성된 유저의 응답 DTO
     */
    public UserResponseDto saveUser(UserRequestDto dto) {
        // 새로운 User 객체 생성 (username, email 정보)
        User user = new User(dto.getUsername(), dto.getEmail());

        // 유저 정보 저장 후 반환
        user = userRepository.save(user);

        // 저장된 유저의 정보를 UserResponseDto로 변환하여 반환
        return new UserResponseDto(user.getId(), user.getUsername(), user.getEmail(), user.getCreatedAt());
    }

    /**
     * 특정 유저 조회
     * @param userId 조회할 유저의 ID
     * @return 조회된 유저의 응답 DTO
     */
    public UserResponseDto findUserById(Long userId) {
        // ID를 기준으로 유저를 찾음, 존재하지 않으면 NOT_FOUND 예외 발생
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // 유저 정보를 UserResponseDto로 변환하여 반환
        return new UserResponseDto(user.getId(), user.getUsername(), user.getEmail(), user.getCreatedAt());
    }

    /**
     * 유저 정보 수정
     * @param userId 수정할 유저의 ID
     * @param dto 수정할 유저 정보 (username, email)
     * @return 수정된 유저의 응답 DTO
     */
    public UserResponseDto updateUser(Long userId, UserRequestDto dto) {
        // ID로 유저를 찾고, 존재하지 않으면 NOT_FOUND 예외 발생
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // 유저의 username, email을 수정
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());

        // 수정된 유저 정보 저장
        userRepository.save(user);

        // 수정된 유저 정보를 응답 DTO로 반환
        return new UserResponseDto(user);
    }

    /**
     * 유저 삭제
     * @param userId 삭제할 유저의 ID
     */
    public void deleteUser(Long userId) {
        // ID로 유저를 찾고, 존재하지 않으면 NOT_FOUND 예외 발생
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // 유저 삭제
        userRepository.delete(user);
    }
}
