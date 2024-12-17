package com.example.scheduledevlv8.repository;

import com.example.scheduledevlv8.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Login, Long> {

    // 이메일로 사용자를 찾는 메소드
    Optional<Login> findByEmail(String email);
}
