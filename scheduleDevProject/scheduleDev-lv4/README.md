# 일정 관리 API

## ⏰ 개발기간
- **2024.12.12 (목) - 2024.12.17 (화)**

---

## 🙍🏻‍♀️ 개발자 소개
- **김리은**: 스파르타 내일배움캠프 Spring 4기 12조 팀원

---

## 📖 프로젝트 설명

이 프로젝트는 일정 관리 API로, 사용자가 일정을 생성, 조회, 수정 및 삭제할 수 있도록 지원합니다. 일정은 사용자와 연동되어 있으며, 각 사용자는 여러 개의 일정을 관리할 수 있습니다. 또한, 유저 생성, 조회, 수정, 삭제와 같은 사용자 관리 기능도 제공합니다.
또한, 로그인 시 세션 ID를 생성하여 이를 저장소에 관리하고, 사용자가 로그인 상태를 유지할 수 있도록 지원합니다. 이를 통해 인증된 사용자만 일정 및 댓글 관리 기능을 이용할 수 있으며, 세션을 통해 사용자 경험을 보다 안전하고 원활하게 제공합니다.

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

### 4. 세션 관리
- **세션 생성**: 사용자가 로그인하면 고유한 세션 ID가 생성되어, 해당 세션 정보를 서버에 저장합니다.
- **세션 저장**: 생성된 세션 ID는 사용자와 연결되어 인증된 상태를 유지합니다.
- **세션 검증**: 사용자가 요청을 보낼 때마다, 서버는 요청 헤더나 쿠키에서 전달된 세션 ID를 확인하여 세션의 유효성을 검증합니다.
- **세션 종료**: 사용자가 로그아웃할 경우, 해당 세션을 서버에서 삭제하여 더 이상 유효하지 않게 만듭니다.

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

### 관계
- `Schedule` 엔터티는 `User`와 **다대일 (Many-to-One)** 관계로 연결됩니다. 즉, 여러 일정이 하나의 사용자에 속할 수 있습니다.

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

## 🚀 로그인 및 로그아웃 API

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