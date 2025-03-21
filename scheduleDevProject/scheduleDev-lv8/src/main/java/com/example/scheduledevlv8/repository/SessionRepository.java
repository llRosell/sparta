package com.example.scheduledevlv8.repository;

import com.example.scheduledevlv8.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, String> {

    Optional<Session> findBySessionId(String sessionId);  // 세션ID로 세션 정보 찾기
}

