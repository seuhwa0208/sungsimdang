# 대전교통공사 통합운영관리시스템 — Frontend

Vue 3 + JavaScript + Vite + Pinia + Vue Router + Axios + **Tailwind CSS 4**
백엔드: Spring Boot 4.1.x / Java 21 (`http://localhost:8088`)

## 실행

```bash
npm install
npm run dev        # 실제 백엔드 사용: /api → http://localhost:8088 프록시 (5173)
npm run dev:mock   # 백엔드 없이 화면 확인: src/mocks 의 메모리 데이터 사용 (상단에 MOCK 배너 표시)
npm run build
```

Mock 계정: `admin` / `manager01` / `user01` (비밀번호 모두 `Test123!`). Mock 은 `src/mocks/` 안에만 있고,
`npm run dev:mock` 이 아니면 로드되지 않는다. 실제 모드에서는 가짜 성공을 표시하지 않는다.

## 구조

```
src/
  assets/styles/main.css      Tailwind 4 (@import "tailwindcss", @theme)
  components/common|board|bike|facility
  composables/                useListView(검색·필터·페이징), useSubmit, useAsyncData
  constants/locations.js      위치 드롭다운 임시 목록 (실제 목록 확정 시 교체)
  layouts/                    MainLayout, DashboardLayout
  mocks/                      Mock 전용 (axios adapter + 시드 데이터)
  router/index.js             권한 가드 (auth / roles / guestOnly)
  services/                   API 경로는 모두 여기에서만 관리
  stores/authStore.js         세션 사용자 (GET /api/members/me 가 기준)
  utils/                      statusLabels(한글 레이블↔Enum 분리), formatDate, errors, validators ...
  views/                      home auth member bike facility notice data-room qna dashboard
```

## 설계 원칙 (PRD 반영)

- 자전거는 `Bike.id` 로만 식별한다 (`bikeNo` 미사용). 표시 형식은 `utils/format.js` 한 곳.
- 정비 이력은 "새 기록 추가"만 지원하고, 이력 행의 당시 상태와 현재 상태를 분리해서 표시한다.
- Enum 은 서버 값 그대로 송수신(`HANDEL` 철자 포함), 한글은 표시용 레이블만.
- 목록 API 가 배열이면 Vue 에서 검색·필터·페이징, `Page` 응답이면 서버 페이징으로 자동 전환.
- 게시글 본문은 `v-html` 없이 텍스트로 렌더링. 자료실은 파일 업로드 UI 없음.
- 세션(JSESSIONID) + CSRF: `GET /api/csrf` 로 토큰을 받아 변경 요청에 헤더로 전달, 403 시 1회만 재발급·재시도.

## 아직 확정되지 않은 부분 (services/ 의 TODO 참고)

| 항목 | 현재 처리 |
|---|---|
| 시설/공지/자료실/Q&A REST 경로 | PRD 9.3 의 *제안* 경로로 구현. Controller 확정 시 `services/*.js` 만 수정 |
| `/api/csrf` 응답 형식 | `{ token, headerName }` 가정 (`services/api.js`) |
| 내가 쓴 글 API | `GET /api/qna/me` 가정, 404 면 "API 미연결" 안내 |
| 회원 탈퇴 | `DELETE /api/members/me/delete` + `{ password }` 가정 |
| 위치 목록 | 대전 1호선 역명 임시 목록 |
| 대시보드 통계 | 전용 API 없음 → 목록 API 로 집계. "최근 정비 이력" 패널은 미구현 |
| Q&A 공개 범위 / 수정 가능 조건 | 서버 응답(403/409)에 따름 |
| 아이디 중복확인 | 와이어프레임에 있음. `GET /api/members/check-login-id` 가정, 404/405 면 안내만 표시 |
| 관리자 로그인 버튼 | 서버 로그인 API 는 하나. 로그인 후 MANAGER/ADMIN 권한을 확인 |

## 와이어프레임(.fig) / ERD 와의 차이 (참고용 — 팀 확인 필요)

와이어프레임은 참고 자료이며, 코드(DTO/Enum)와 다른 부분은 **코드 기준**으로 구현했다.

| 항목 | 와이어프레임 / ERD | 구현 |
|---|---|---|
| 자전거 번호 `BIKE-001` | 목록·정비 화면에 표시, ERD 에도 `bike_no`(UK) 존재. 단 자전거 등록 폼은 "위치"만 입력 | PRD 결정대로 `id` 표시(`#1`). 표시 형식은 `utils/format.js` |
| 시설명 | 목록·등록 화면에 "시설명" | ERD·DTO 에 없음 → 시설 번호(`facilityNo`)/유형/위치로 표시 |
| 시설 유형 | 에스컬레이터·승강장 시설·조명·소방 등 | 백엔드 Enum 4종만 지원 (BIKE_STATION, BIKE_RACK, ELEVATOR, SCREEN_DOOR) |
| 상태 이름 | 정상/정비중/고장/운영중지 혼용 | Enum 3종(AVAILABLE/REPAIRING/UNAVAILABLE) → 이용가능·정비중·운영중지 |
| 대여하기 버튼, 지도 보기, 최근 타슈 이용 현황(대여소별 이용 횟수) | 있음 | 대여/이용 이력/지도 API·테이블이 없어 미구현 (PRD 범위 외) |
| 자료실 첨부파일 업로드(10MB) | 있음 | ERD(DATAFILES)에 파일 컬럼 없음, PRD 상 제외 → 미구현 |
| 회원가입 | 아이디·비밀번호·이름·이메일 | 백엔드가 약관/개인정보 동의 필수(`@AssertTrue`)라 동의 체크 + 선택 전화번호 추가 |
| 비밀번호 규칙 | "8자 이상" | 백엔드 DTO 기준 4~30자 + 영문/숫자/특수문자 |
| 조회수·작성자 | 공지/자료실/Q&A 목록에 표시 | ERD 상 Q&A 만 `hits` 존재. 응답에 값이 있을 때만 컬럼 표시 |
| 정비 이력 `status` | ERD 의 *MAINTENANCE 테이블에 상태 컬럼 없음 | 코드(Entity/DTO)에는 있으므로 코드 기준 표시 |
