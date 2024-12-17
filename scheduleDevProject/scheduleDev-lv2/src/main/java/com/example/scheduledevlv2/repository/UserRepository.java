package com.example.scheduledevlv2.repository;

import com.example.scheduledevlv2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);

    // 또는 위와 같은 방식으로 별도의 메서드를 정의할 수 있습니다.
    default User findUserById(Long id) {
        return findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NO_CONTENT, "Does not exist id = " + id));
    }
}
