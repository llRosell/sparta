# 일정 관리 애플리케이션

이 프로젝트는 일정 관리 API로, 사용자가 일정을 생성, 조회, 수정 및 삭제할 수 있도록 지원합니다.

# API 명세서

| Aa 기능       | Method | URL                       | request         | response      | 상태코드    |
|---------------|--------|---------------------------|------------------|---------------|-------------|
| 일정 등록     | POST   | /api/schedules             | 요청 body       | 등록된 일정 정보 | 200: 정상등록 |
| 일정 조회     | GET    | /api/schedules/{scheduleId} | 요청 param      | 단건 응답 정보 (일정) | 200: 정상조회 |
| 일정 목록 조회 | GET    | /api/schedules             | 요청 param      | 일정 목록 (배열) | 200: 정상조회 |
| 일정 수정     | PUT    | /api/schedules/{scheduleId} | 요청 body       | 수정된 일정 정보 | 200: 정상수정 |
| 일정 삭제     | DELETE | /api/schedules/{scheduleId} | 요청 param      | -             | 200: 정상삭제 |


## API 엔드포인트

- **POST** `/api/schedules`: 일정 생성
- **GET** `/api/schedules`: 전체 일정 조회
- **GET** `/api/schedules/{scheduleId}`: 특정 일정 조회
- **PUT** `/api/schedules/{scheduleId}`: 특정 일정 수정
- **DELETE** `/api/schedules/{scheduleId}`: 특정 일정 삭제

## 요청 및 응답 형식

각 API의 요청 및 응답 형식은 위의 표를 참조해 주세요.

### 요청 Body 예시 (일정 생성 및 수정)

```json
{
    "todo": "할 일",
    "name": "작성자 이름",
    "password": "비밀번호",
    "created": "작성일",
    "updated": "수정일"
}
