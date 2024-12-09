CREATE TABLE schedule (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- 기본 키로 사용되는 id
                          todo VARCHAR(255) NOT NULL,            -- 할 일 내용을 저장하는 컬럼
                          name VARCHAR(100) NOT NULL,            -- 작성자 이름을 저장하는 컬럼
                          password VARCHAR(100),                 -- 비밀번호 컬럼 (null 허용)
                          created DATETIME NOT NULL,             -- 일정 생성 시간을 저장하는 컬럼
                          updated DATETIME NOT NULL              -- 일정 수정 시간을 저장하는 컬럼
);