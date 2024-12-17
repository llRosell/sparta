package com.example.scheduledevlv8.service;

import com.example.scheduledevlv8.dto.UserRequestDto;
import com.example.scheduledevlv8.dto.UserResponseDto;
import com.example.scheduledevlv8.entity.User;
import com.example.scheduledevlv8.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 유저 생성
    public UserResponseDto saveUser(UserRequestDto dto) {
        User user = new User(dto.getUsername(), dto.getEmail());
        user = userRepository.save(user);  // 저장된 유저를 반환

        // 저장된 유저 정보를 UserResponseDto로 변환하여 반환
        return new UserResponseDto(user.getId(), user.getUsername(), user.getEmail(), user.getCreatedAt());
    }

    // 특정 유저 조회
    public UserResponseDto findUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return new UserResponseDto(user.getId(), user.getUsername(), user.getEmail(), user.getCreatedAt());
    }

    // 유저 수정
    public UserResponseDto updateUser(Long userId, UserRequestDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        userRepository.save(user);

        return new UserResponseDto(user);
    }

    // 유저 삭제
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        userRepository.delete(user);
    }
}
