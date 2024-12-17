package com.example.scheduledevlv2.service;

import com.example.scheduledevlv2.dto.UserRequestDto;
import com.example.scheduledevlv2.dto.UserResponseDto;
import com.example.scheduledevlv2.entity.User;
import com.example.scheduledevlv2.repository.UserRepository;
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
    public User saveUser(String username, String email) {
        User user = new User(username, email);
        return userRepository.save(user); // User 엔티티 저장
    }

    // 유저 조회
    public UserResponseDto findUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            // 유저가 없을 경우 예외 처리
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Does not exist id : " + id);
        }
        // User 객체를 가져오고, UserResponseDto로 변환
        User findUser = optionalUser.get();
        return new UserResponseDto(findUser.getId(), findUser.getUsername(), findUser.getEmail());
    }

    @Transactional
    public UserResponseDto updateUser(Long id, UserRequestDto dto) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "작성자를 찾을 수 없습니다.");
        }

        User user = optionalUser.get();

        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());

        // 데이터베이스에 업데이트 반영
        userRepository.save(user);

        // 업데이트된 AuthorResponseDto 반환
        return new UserResponseDto(user);
    }


    // 유저 삭제
    public void deleteUser(Long id) {
        UserResponseDto userResponseDto = findUserById(id);  // UserResponseDto를 반환받음
        User user = new User(userResponseDto.getUsername(), userResponseDto.getEmail());  // User 객체로 변환
        userRepository.delete(user);  // User 삭제
    }
}
