package com.example.scheduledevtesterlv1.repository;

import com.example.scheduledevtesterlv1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// JpaRepository를 상속받아 User 엔티티에 대한 CRUD 작업을 지원하는 인터페이스
public interface UserRepository extends JpaRepository<User, Long> {

    // 사용자 ID로 유저를 조회하는 메서드
    // findById는 이미 JpaRepository에 정의되어 있지만, 명시적으로 사용하기 위해 추가
    Optional<User> findById(Long id);  // 주어진 id로 User를 조회하여 Optional로 반환
}