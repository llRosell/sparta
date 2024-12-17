package com.example.scheduledevlv3.service;

import com.example.scheduledevlv3.dto.UserRequestDto;
import com.example.scheduledevlv3.dto.UserResponseDto;
import com.example.scheduledevlv3.entity.User;
import com.example.scheduledevlv3.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 유저 생성
    @Transactional
    public User saveUser(UserRequestDto dto) {
        User user = new User(dto.getUsername(), dto.getEmail(), dto.getPassword());
        return userRepository.save(user); // User 엔티티 저장
    }

    // 유저 조회
    public UserResponseDto findUserById(Long id, String password) {
        Optional<User> optionalUser = userRepository.findById(id);

        // 작성자를 찾지 못한 경우 예외 발생
        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "작성자를 찾을 수 없습니다.");
        }

        User user = optionalUser.get();

        // 비밀번호 확인
        if (!user.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호가 일치하지 않습니다.");
        }

        return new UserResponseDto(user); // UserResponseDto 반환
    }

    // 유저 수정
    @Transactional // 트랜잭션을 사용하여 데이터베이스 변경 관리
    public UserResponseDto updateUser(Long id, UserRequestDto dto, String password) {
        Optional<User> optionalUser = userRepository.findById(id);

        // 작성자를 찾지 못한 경우 예외 발생
        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "작성자를 찾을 수 없습니다.");
        }

        User user = optionalUser.get();

        // 비밀번호 검증
        if (!user.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        // USer 필드 업데이트
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());

        user = userRepository.save(user);  // 업데이트 반영
        return new UserResponseDto(user);
    }

    // 유저 삭제
    public void deleteUser(Long id, String password) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다.");
        }

        User user = optionalUser.get();
        if (!user.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호가 일치하지 않습니다.");
        }

        userRepository.deleteById(id);
    }
}
