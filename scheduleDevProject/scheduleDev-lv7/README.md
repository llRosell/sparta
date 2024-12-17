# 일정 관리 API

## ⏰ 개발기간
- **2024.12.12 (목) - 2024.12.17 (화)**

---

## 🙍🏻‍♀️ 개발자 소개
- **김리은**: 스파르타 내일배움캠프 Spring 4기 12조 팀원

---

## 📖 프로젝트 설명

이 프로젝트는 일정 관리 API로, 사용자가 일정을 생성, 조회, 수정 및 삭제할 수 있도록 지원합니다. 일정은 사용자와 연동되어 있으며, 각 사용자는 여러 개의 일정을 관리할 수 있습니다. 
또한, 유저 생성, 조회, 수정, 삭제와 같은 사용자 관리 기능도 제공합니다.
일정과 댓글을 맵핑하여 댓글을 생성, 조회, 수정, 삭제하는 기능을 제공합니다. 댓글은 특정 일정에 대해 사용자가 작성하며, 각 댓글은 해당 일정과 사용자에 맵핑됩니다.


---

## 🌟 주요 기능

### 1. 일정 관리
- **일정 생성**: 사용자가 새로운 일정을 추가할 수 있습니다.
- **일정 조회**: 사용자가 등록한 일정을 조회할 수 있습니다.
- **일정 수정**: 사용자가 기존 일정을 수정할 수 있습니다.
- **일정 삭제**: 사용자가 더 이상 필요 없는 일정을 삭제할 수 있습니다.

### 2. 작성자 관리
- **유저 생성**: 사용자가 새로운 유저를 생성할 수 있습니다.
- **유저 조회**: 특정 유저의 정보를 조회할 수 있습니다.
- **유저 수정**: 사용자가 기존 유저 정보를 수정할 수 있습니다.
- **유저 삭제**: 유저를 삭제할 수 있습니다.

### 3. 사용자 관리
- **회원가입**: 사용자가 이메일과 비밀번호를 사용하여 새 계정을 만들 수 있습니다. 회원가입 시 비밀번호는 암호화되어 저장됩니다.
- **로그인**: 사용자가 이메일과 비밀번호로 로그인하여 인증을 받습니다.
- **로그아웃**: 사용자가 로그인된 세션을 종료하고 로그아웃할 수 있습니다.

### 4.댓글 관리
- **댓글 생성**: 사용자가 특정 일정에 대해 댓글을 작성할 수 있습니다.
- **댓글 조회**: 특정 일정과 사용자에 해당하는 댓글을 조회할 수 있습니다.
- **댓글 수정**: 사용자가 자신의 댓글을 수정할 수 있습니다.
- **댓글 삭제**: 사용자가 자신의 댓글을 삭제할 수 있습니다.

---

## 🔐 비밀번호 암호화

이 프로젝트에서는 사용자의 비밀번호를 안전하게 저장하기 위해 **BCrypt** 암호화 알고리즘을 사용합니다. BCrypt는 비밀번호를 해시화하여 데이터베이스에 저장하며, 암호화된 비밀번호를 복호화할 수 없습니다. 대신, 로그인 시 입력된 비밀번호와 저장된 암호화된 비밀번호를 비교하여 인증합니다.

### 사용 방법

1. **BCrypt 설정**

   `PasswordEncryptionService` 클래스를 사용하여 비밀번호를 암호화합니다.

---

## 🚦 유효성 검사 개요

이 프로젝트는 사용자가 전송한 데이터에 대해 유효성 검사를 수행하여 잘못된 데이터를 서버에서 처리하지 않도록 보장합니다. `@Validated`와 같은 어노테이션을 사용하여 요청 데이터가 요구 사항을 충족하는지 검증하며, 검증 실패 시 적절한 오류 메시지를 반환합니다.

## 📝 DTO에서의 유효성 검사

각 DTO 클래스에는 데이터의 유효성을 확인하기 위한 어노테이션이 포함되어 있습니다. 예를 들어, 사용자 등록 요청(`SignUpRequestDto`)에서는 이메일과 비밀번호에 대해 필수 입력 및 형식 검사를 진행합니다. `@NotBlank`, `@Pattern`, `@Size` 등의 어노테이션을 사용하여 입력값에 대한 조건을 명확히 정의합니다.

**예시:**

```java
@NotBlank(message = "이메일은 필수입니다.")
@Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "올바른 이메일 형식을 입력해주세요.")
private String email;

@NotBlank(message = "비밀번호는 필수입니다.")
private String password;
```

---

## 📑 API 명세서

### 일정 관련 API

| 기능           | Method | URL                      | Request           | Response           | 상태코드       |
|----------------|--------|--------------------------|-------------------|--------------------|----------------|
| 일정 등록      | POST   | /schedules               | 요청 본문          | 등록된 일정 정보     | 201: 정상 등록  |
| 일정 조회      | GET    | /schedules/{scheduleId}  | 요청 파라미터       | 단건 응답 정보 (일정) | 200: 정상 조회  |
| 일정 목록 조회 | GET    | /schedules               | 요청 파라미터       | 일정 목록 (배열)      | 200: 정상 조회  |
| 일정 수정      | PUT    | /schedules/{scheduleId}  | 요청 본문          | 수정된 일정 정보     | 200: 정상 수정  |
| 일정 삭제      | DELETE | /schedules/{scheduleId}  | 요청 파라미터       | -                  | 200: 정상 삭제  |

### 유저 관련 API

| 기능           | Method | URL                      | Request           | Response           | 상태코드       |
|----------------|--------|--------------------------|-------------------|--------------------|----------------|
| 유저 생성      | POST   | /users                   | 요청 본문          | 생성된 유저 정보    | 201: 정상 생성  |
| 유저 조회      | GET    | /users/{userId}          | 요청 파라미터       | 단건 응답 정보 (유저) | 200: 정상 조회  |
| 유저 수정      | PUT    | /users/{userId}          | 요청 본문          | 수정된 유저 정보    | 200: 정상 수정  |
| 유저 삭제      | DELETE | /users/{userId}          | 요청 파라미터       | -                  | 200: 정상 삭제  |

### 회원가입 API

| 기능           | Method | URL                      | Request         | Response           | 상태코드       |
|----------------|--------|--------------------------|-----------------|--------------------|----------------|
| 회원가입       | POST   | /signup                  | 이메일, 비밀번호       | 회원가입 성공 정보   | 201: 정상 가입  |


### 로그인 API

| 기능           | Method | URL                      | Request           | Response           | 상태코드       |
|----------------|--------|--------------------------|-------------------|--------------------|----------------|
| 로그인         | POST   | /login                   | 이메일, 비밀번호  | 로그인 성공 정보   | 200: 로그인 성공 |
| 로그인 실패    | POST   | /login                   | 이메일, 비밀번호  | 401 Unauthorized   | 401: 로그인 실패 |

### 로그아웃 API

| 기능           | Method | URL                      | Request           | Response           | 상태코드       |
|----------------|--------|--------------------------|-------------------|--------------------|----------------|
| 로그아웃       | POST   | /logout                  | -                 | 로그아웃 성공 메시지| 200: 로그아웃 성공 |

### 댓글 관련 API

| 기능           | Method | URL                                     | Request             | Response              | 상태코드        |
|----------------|--------|-----------------------------------------|---------------------|-----------------------|-----------------|
| 댓글 생성      | POST   | /comments                               | 요청 본문 (댓글 정보)  | 생성된 댓글 정보       | 201: 정상 생성   |
| 댓글 조회      | GET    | /comments/schedule/{scheduleId}/user/{userId} | 요청 파라미터 (일정 ID, 사용자 ID) | 댓글 목록 (배열)      | 200: 정상 조회   |
| 댓글 수정      | PUT    | /comments/{commentId}/user/{userId}/schedule/{scheduleId} | 요청 본문 (수정된 댓글 내용) | 수정된 댓글 정보       | 200: 정상 수정   |
| 댓글 삭제      | DELETE | /comments/{commentId}/user/{userId}/schedule/{scheduleId} | 요청 파라미터 (댓글 ID, 사용자 ID, 일정 ID) | -                     | 200: 정상 삭제   |

---

## 🧩 엔터티-관계 다이어그램 (ERD)

### Schedule 엔터티

| 속성명       | 타입             | 제약 조건       | 설명              |
|--------------|------------------|----------------|-------------------|
| `id`         | Long (PK)        | 자동 생성       | 기본 키 (ID)       |
| `title`      | String           | Not Null       | 할 일 제목        |
| `contents`   | String (longtext)| Nullable       | 할 일 내용        |
| `createdAt`  | Timestamp        | 자동 생성       | 생성 시각         |
| `modifiedAt` | Timestamp        | 자동 업데이트   | 마지막 수정 시각   |
| `userId`     | Long (FK)        | Not Null       | 사용자 ID         |

### User 엔터티

| 속성명       | 타입             | 제약 조건       | 설명              |
|--------------|------------------|----------------|-------------------|
| `id`         | Long (PK)        | 자동 생성       | 기본 키 (ID)       |
| `username`   | String           | Not Null       | 사용자 이름       |
| `email`      | String           | Not Null       | 이메일            |
| `createdAt`  | Timestamp        | 자동 생성       | 생성 시각         |

### Comment 엔터티

| 속성명       | 타입             | 제약 조건       | 설명              |
|--------------|------------------|----------------|-------------------|
| `id`         | Long (PK)        | 자동 생성       | 기본 키 (ID)       |
| `content`    | String           | Not Null       | 댓글 내용         |
| `createdAt`  | Timestamp        | 자동 생성       | 생성 시각         |
| `modifiedAt` | Timestamp        | 자동 업데이트   | 수정 시각         |

### 관계
- `Schedule` 엔터티는 `User`와 **다대일 (Many-to-One)** 관계로 연결됩니다. 즉, 여러 일정이 하나의 사용자에 속할 수 있습니다.
- `Comment` 엔터티는 `User`와 **다대일 (Many-to-One)** 관계로 연결됩니다. 즉, 하나의 사용자가 여러 댓글을 작성할 수 있습니다.
- `Comment` 엔터티는 `Schedule`과 **다대일 (Many-to-One)** 관계로 연결됩니다. 즉, 여러 댓글이 하나의 일정에 속할 수 있습니다.

---

## 🚀 Schedule API 엔드포인트

### 1. 일정 등록

- **HTTP 메서드**: `POST`
- **경로**: `/schedules?userId={userId}`
- **요청 본문**:
    ```json
    {
        "title": "일정 제목",
        "contents": "일정 내용"
    }
    ```
- **응답 본문**:
    ```json
    {
        "id": "Long",
        "title": "String",
        "contents": "String",
        "userId": "Long",
        "createdAt": "Timestamp",
        "modifiedAt": "Timestamp"
    }
    ```
- **상태 코드**: `201 Created`

---

### 2. 일정 조회 (단건)

- **HTTP 메서드**: `GET`
- **경로**: `/schedules/{scheduleId}?userId={userId}`
- **요청 파라미터**:
  - `scheduleId` (경로 변수)
- **응답 본문**:
    ```json
    {
        "id": "Long",
        "title": "String",
        "contents": "String",
        "userId": "Long",
        "createdAt": "Timestamp",
        "modifiedAt": "Timestamp"
    }
    ```
- **상태 코드**: `200 OK`

---

### 3. 일정 목록 조회 (할 일 전체 조회)

- **HTTP 메서드**: `GET`
- **경로**: `/schedules?userId={userId}`
- **요청 파라미터**: 없음
- **응답 본문**:
    ```json
    [
        {
            "id": "Long",
            "title": "String",
            "contents": "String",
            "userId": "Long",
            "createdAt": "Timestamp",
            "modifiedAt": "Timestamp"
        },
        ...
    ]
    ```
- **상태 코드**: `200 OK`

---

### 4. 일정 수정

- **HTTP 메서드**: `PUT`
- **경로**: `/schedules/{scheduleId}?userId={userId}`
- **요청 본문**:
    ```json
    {
        "title": "수정된 일정 제목",
        "contents": "수정된 일정 내용"
    }
    ```
- **응답 본문**:
    ```json
    {
        "id": "Long",
        "title": "String",
        "contents": "String",
        "userId": "Long",
        "createdAt": "Timestamp",
        "modifiedAt": "Timestamp"
    }
    ```
- **상태 코드**: `200 OK`

---

### 5. 일정 삭제

- **HTTP 메서드**: `DELETE`
- **경로**: `/schedules/{scheduleId}?userId={userId}`
- **요청 파라미터**:
  - `scheduleId` (경로 변수)
- **응답 본문**: 없음
- **상태 코드**: `200 OK`

---

## 🚀 User API 엔드포인트

### 1. 유저 생성

- **HTTP 메서드**: `POST`
- **경로**: `/users`
- **요청 본문**:
    ```json
    {
        "username": "사용자 이름",
        "email": "사용자 이메일"
    }
    ```
- **응답 본문**:
    ```json
    {
        "createdAt": "Timestamp",
        "id": "Long",
        "username": "String",
        "email": "String"
    }
    ```
- **상태 코드**: `201 Created`

---

### 2. 유저 조회 (단건)

- **HTTP 메서드**: `GET`
- **경로**: `/users/{userId}`
- **요청 파라미터**:
  - `userId` (경로 변수)
- **응답 본문**:
    ```json
    {
        "createdAt": "Timestamp",
        "id": "Long",
        "username": "String",
        "email": "String"
    }
    ```
- **상태 코드**: `200 OK`

---

### 3. 유저 수정

- **HTTP 메서드**: `PUT`
- **경로**: `/users/{userId}`
- **요청 본문**:
    ```json
    {
        "username": "새로운 사용자 이름",
        "email": "새로운 사용자 이메일"
    }
    ```
- **응답 본문**:
    ```json
    {
        "createdAt": "Timestamp",
        "id": "Long",
        "username": "String",
        "email": "String"
    }
    ```
- **상태 코드**: `200 OK`

---

### 4. 유저 삭제

- **HTTP 메서드**: `DELETE`
- **경로**: `/users/{userId}`
- **요청 파라미터**:
  - `userId` (경로 변수)
- **응답 본문**: 없음
- **상태 코드**: `200 OK`

---

## 🚀 회원가입 API 엔드포인트

- **HTTP 메서드**: `POST`
- **경로**: `/signup`
- **요청 본문**:
    ```json
    {
        "email": "사용자 이메일",
        "password": "사용자 비밀번호"
    }
    ```
- **응답 본문**:
    ```json
    {
        "email": "String",
        "message": "Signup successful"
    }
    ```
- **상태 코드**: `201 Created`
  - 회원가입 성공 시: `201 Created`
  - 이메일 중복 시: `409 Conflict` (이미 가입된 이메일일 경우)
  - 잘못된 요청 시: `400 Bad Request` (필수 입력값 누락 또는 형식 오류)

---

## 🚀 로그인 및 로그아웃 API 엔드포인트

### 1. 로그인

- **HTTP 메서드**: `POST`
- **경로**: `/login`
- **요청 본문**:
    ```json
    {
        "email": "사용자 이메일",
        "password": "사용자 비밀번호"
    }
    ```
- **응답 본문**:
    ```json
    {
        "message": "Login successful"
    }
    ```
- **상태 코드**: `200 OK`
  - 로그인 성공 시: `200 OK`
  - 로그인 실패 시: `401 Unauthorized` (비밀번호 불일치 또는 이메일 없음)

---

### 2. 로그아웃

- **HTTP 메서드**: `POST`
- **경로**: `/logout`
- **요청 본문**: 없음
- **응답 본문**:
    ```json
    {
        "message": "Logout successful"
    }
    ```
- **상태 코드**: `200 OK`
---

## 🚀 comment API 엔드포인트

### 1. 댓글 등록

- **HTTP 메서드**: `POST`
- **경로**: `/comments`
- **요청 본문**:
    ```json
    {
        "userId": "Long",
        "scheduleId": "Long",
        "content": "String"
    }
    ```
- **응답 본문**:
    ```json
    {
        "id": "Long",
        "content": "String",
        "userId": "Long",
        "scheduleId": "Long",
        "createdAt": "Timestamp",
        "modifiedAt": "Timestamp"
    }
    ```
- **상태 코드**: `201 Created`

---

### 2. 댓글 목록 조회 (일정에 대한 전체 댓글 조회)

- **HTTP 메서드**: `GET`
- **경로**: `/comments/schedule/{scheduleId}/user/{userId}`
- **응답 본문**:
    ```json
    [
        {
            "id": "Long",
            "content": "String",
            "userId": "Long",
            "scheduleId": "Long",
            "createdAt": "Timestamp",
            "modifiedAt": "Timestamp"
        },
        ...
    ]
    ```
- **상태 코드**: `200 OK`

---

### 3. 댓글 수정

- **HTTP 메서드**: `PUT`
- **경로**: `/comments/{commentId}/schedule/{scheduleId}/user/{userId}`
- **요청 파라미터**:
  - `commentId` (경로 변수)
- **요청 본문**:
    ```json
    {
        "content": "수정된 댓글 내용"
    }
    ```
- **응답 본문**:
    ```json
    {
        "id": "Long",
        "content": "String",
        "userId": "Long",
        "scheduleId": "Long",
        "createdAt": "Timestamp",
        "modifiedAt": "Timestamp"
    }
    ```
- **상태 코드**: `200 OK`

---

### 4. 댓글 삭제

- **HTTP 메서드**: `DELETE`
- **경로**: `/comments/{commentId}/schedule/{scheduleId}/user/{userId}`
- **요청 파라미터**:
  - `commentId` (경로 변수)
- **응답 본문**: 없음
- **상태 코드**: `200 OK`

---
