# 일정관리 앱 만들기 Project

## ⏰ 개발기간
- 2024.12.12 (목) - 2024.12.17 (화)

---

## 🙍🏻‍♀️ 개발자 소개
- **김리은**: 스파르타 내일배움캠프 Spring 4기 12조 팀원

---

## 📖 프로젝트 설명

이 프로젝트는 일정 관리 API로, 사용자가 일정을 생성, 조회, 수정 및 삭제할 수 있도록 지원합니다.

---

# API 명세서

| 기능           | Method | URL                      | Request           | Response           | 상태코드       |
|----------------|--------|--------------------------|-------------------|--------------------|----------------|
| 일정 등록      | POST   | /schedules               | 요청 본문          | 등록된 일정 정보     | 201: 정상 등록  |
| 일정 조회      | GET    | /schedules/{scheduleId}  | 요청 파라미터       | 단건 응답 정보 (일정) | 200: 정상 조회  |
| 일정 목록 조회 | GET    | /schedules               | 요청 파라미터       | 일정 목록 (배열)      | 200: 정상 조회  |
| 일정 수정      | PUT    | /schedules/{scheduleId}  | 요청 본문          | 수정된 일정 정보     | 200: 정상 수정  |
| 일정 삭제      | DELETE | /schedules/{scheduleId}  | 요청 파라미터       | -                  | 200: 정상 삭제  |

---

# 엔터티-관계 다이어그램 (ERD)

## 엔터티: Schedule

| 속성명       | 타입             | 제약 조건       | 설명              |
|--------------|------------------|----------------|-------------------|
| `id`         | Long (PK)        | 자동 생성       | 기본 키 (ID)       |
| `username`   | String           | Not Null       | 사용자 이름       |
| `title`      | String           | Not Null       | 할 일 제목        |
| `contents`   | String (longtext)| Nullable       | 할 일 내용        |
| `createdAt`  | Timestamp        | 자동 생성       | 생성 시각         |
| `modifiedAt`  | Timestamp        | 자동 업데이트   | 마지막 수정 시각   |

### 관계
- 현재 제공된 코드에서는 다른 엔터티와의 관계는 정의되지 않았습니다.
- `Schedule` 엔터티는 단일 테이블 구조로 동작합니다.

---

## Schedule API 엔드포인트

| HTTP 메서드 | 경로               | 요청 본문                | 응답                              | 설명                          |
|-------------|--------------------|--------------------------|---------------------------------|-------------------------------|
| **POST**    | `/schedules`       | `CreateScheduleRequestDto` | `ScheduleResponseDto` (201)     | 새로운 일정을 생성합니다.       |
| **GET**     | `/schedules`       | 없음                     | `ScheduleResponseDto` 리스트 (200) | 모든 일정을 조회합니다.         |
| **GET**     | `/schedules/{id}`  | 없음                     | `ScheduleResponseDto` (200)     | 특정 ID의 일정을 조회합니다.    |
| **PUT**     | `/schedules/{id}`  | `CreateScheduleRequestDto` | `ScheduleResponseDto` (200)     | 특정 ID의 일정을 수정합니다.    |
| **DELETE**  | `/schedules/{id}`  | 없음                     | 없음 (200)                        | 특정 ID의 일정을 삭제합니다.    |

---

## Schedule 엔터티 JSON 형식

```json
{
  "id": "Long",
  "username": "String",
  "title": "String",
  "contents": "String",
  "createdAt": "Timestamp",
  "modifiedAt": "Timestamp"
}
