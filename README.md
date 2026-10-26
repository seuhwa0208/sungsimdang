# 대전교통공사 통합운영관리시스템

> 대전 도시철도 운영에 필요한 **자전거·시설물·회원·게시판 정보**를 통합 관리하기 위한 팀 프로젝트입니다.  
> **안내:** 학습 목적으로 제작한 프로젝트이며, 대전교통공사의 공식 서비스가 아닙니다.

## 목차

1. [프로젝트 소개](#프로젝트-소개)
2. [주요 기능](#주요-기능)
3. [프로젝트 구조](#프로젝트-구조)
4. [GitHub 소스코드 설치 및 실행](#github-소스코드-설치-및-실행)
   - [1단계 — 프로그램 설치](#1단계--프로그램-설치)
   - [2단계 — GitHub에서 소스코드 다운로드](#2단계--github에서-소스코드-다운로드)
   - [3단계 — PostgreSQL 데이터베이스 준비](#3단계--postgresql-데이터베이스-준비)
   - [4단계 — Spring Boot 백엔드 실행](#4단계--spring-boot-백엔드-실행)
   - [5단계 — Vue 프론트엔드 실행](#5단계--vue-프론트엔드-실행)
5. [Docker Hub 이미지 설치 및 실행 가이드](#docker-hub-이미지-설치-및-실행-가이드)
   - [1. Docker Desktop 설치](#1-docker-desktop-설치)
   - [2. Docker 설치 확인](#2-docker-설치-확인)
   - [3. Docker Compose 파일 준비](#3-docker-compose-파일-준비)
   - [4. Docker Hub 이미지 다운로드 및 실행](#4-docker-hub-이미지-다운로드-및-실행)
   - [5. 실행 확인 및 접속](#5-실행-확인-및-접속)
6. [자주 발생하는 오류](#자주-발생하는-오류)
---

## 프로젝트 소개

회원, 관리자(ADMIN), 현장 작업자(MANAGER)가 권한에 맞는 기능을 사용하는 웹 기반 통합 운영관리시스템입니다. **Vue 프론트엔드**가 화면을 제공하고, **Spring Boot REST API**가 데이터 처리·권한 검사·업무 규칙을 담당하며, **PostgreSQL**에 데이터가 저장됩니다.

| 구분 | 기술 |
|---|---|
| 백엔드 | Java 21, Spring Boot, Spring Data JPA, Spring Security, Gradle Wrapper |
| 프론트엔드 | Vue, Vite, Node.js / npm |
| 데이터베이스 | PostgreSQL |
| 개발·실행 환경 | Windows CMD 기준, Docker Desktop 선택 사용 |
| 백엔드 기본 포트 | `8088` |
| 프론트엔드 개발 포트 | Vite가 실행 시 출력하는 포트(보통 `5173`) |

## 주요 기능

| 영역 | 기능 |
|---|---|
| 회원 | 회원가입, 로그인·로그아웃, 마이페이지, 정보·비밀번호 변경, 탈퇴 |
| 관리자 | 회원 조회, 활성화·비활성화, 강퇴, 권한 변경 |
| 자전거 | 등록·수정·삭제, 목록·위치 검색, 상태 변경, 고장 접수, 정비 이력 |
| 시설물 | 등록·수정·삭제, 목록·위치 검색, 상태 변경, 고장 접수, 정비 이력 |
| 공지사항 | 목록·검색·상세 조회, ADMIN 등록·수정·삭제 |
| 자료실 | 목록·검색·상세 조회, ADMIN 등록·수정·삭제 |
| Q&A | 질문 등록·수정·삭제, ADMIN 답변 등록·관리, 답변 상태 조회 |

**권한 구분:** USER는 일반 회원 기능, MANAGER는 여기에 자전거·시설물 현장 관리 기능을 추가로 사용할 수 있으며, ADMIN은 회원·게시판·자전거·시설물을 관리합니다.

## 프로젝트 구조

아래는 프로젝트의 주요 폴더를 단순화한 예시입니다.

```text
sungsimdang/                       ← git clone 후 생성되는 폴더
├── backend/                       ← Spring Boot
│   ├── src/main/java/com/sungsimdang/project/
│   │   ├── bike/
│   │   ├── board/
│   │   ├── config/
│   │   ├── facility/
│   │   └── member/
│   ├── src/main/resources/application.yaml
│   ├── build.gradle
│   ├── gradlew.bat
│   └── gradle/wrapper/
├── frontend/                      ← Vue / Vite
│   ├── src/
│   └── package.json
└── README.md
```

---

## GitHub 소스코드 설치 및 실행

**GitHub 소스코드로 실행하는 경우, 아래 데이터베이스 준비 방법 A/B 중 하나를 선택해 주세요.**

> Docker Hub의 완성된 이미지로 전체 프로젝트를 실행하려면 [Docker Hub 이미지 설치 및 실행 가이드](#docker-hub-이미지-설치-및-실행-가이드)를 이용하세요.

- **방법 A:** PC에 PostgreSQL을 직접 설치하여 사용
- **방법 B:** Docker Desktop에서 PostgreSQL 컨테이너를 실행하여 사용 (별도 PostgreSQL 설치 불필요)

### 1단계 — 프로그램 설치

Windows에서 다음 프로그램을 준비합니다.

| 프로그램 | 용도 | 다운로드 |
|---|---|---|
| Git | GitHub의 코드를 내려받기 | [Git for Windows](https://git-scm.com/downloads/win) |
| JDK 21 | Spring Boot 실행 및 빌드 | [Eclipse Temurin JDK 21](https://adoptium.net/temurin/releases/?version=21) |
| Node.js (LTS) | Vue / Vite 실행 | [Node.js 공식 홈페이지](https://nodejs.org/) |
| PostgreSQL | 방법 A를 선택한 경우에만 설치 | [PostgreSQL Windows 다운로드](https://www.postgresql.org/download/windows/) |
| Docker Desktop | 방법 B 또는 Docker Hub 2-Tier 전체 실행 시 설치 | [Docker Desktop for Windows](https://docs.docker.com/desktop/setup/install/windows-install/) |

### 2단계 — GitHub에서 소스코드 다운로드

#### 2-1. CMD 열기

`Windows 키 + R` → `cmd` 입력 → Enter를 누릅니다.

#### 2-2. 다운로드 폴더로 이동

```bat
cd /d "%USERPROFILE%\Downloads"
```

현재 위치를 확인합니다.

```bat
cd
```

#### 2-3. GitHub 저장소 내려받기

```bat
git clone https://github.com/seuhwa0208/sungsimdang.git
cd sungsimdang
dir
```

- `git clone`: GitHub 저장소를 PC에 **처음 다운로드**합니다.
- `cd sungsimdang`: 다운로드된 프로젝트 폴더로 들어갑니다.
- `dir`: 현재 폴더의 파일 및 하위 폴더를 확인합니다.

---

### 3단계 — PostgreSQL 데이터베이스 준비

#### 방법 A. PostgreSQL 직접 설치

1. PostgreSQL을 설치하고 서버를 실행합니다.
2. PostgreSQL 설치 과정에서 지정한 `postgres` 계정의 비밀번호를 준비합니다.
3. pgAdmin의 Query Tool에서 다음 SQL로 데이터베이스를 생성합니다. 이미 `djtbc`가 있으면 다시 생성하지 않습니다.

```sql
CREATE DATABASE djtbc;
```

4. 연결 정보는 다음과 같습니다.

| 항목 | 값 |
|---|---|
| 서버 주소 | `localhost` |
| 포트 | `5432` |
| 데이터베이스 | `djtbc` |
| 사용자명 | `postgres` |
| 비밀번호 | PostgreSQL 설치 시 지정한 값 |

#### 방법 B. Docker에서 PostgreSQL 실행

**[Docker Desktop 설치](#1-docker-desktop-설치)**에 따라 Docker Desktop을 먼저 설치한 다음, 아래 컨테이너 실행 명령을 사용하세요. 방법 B는 PostgreSQL 프로그램을 Windows에 직접 설치하지 않아도 됩니다.

```bat
docker pull postgres:16
docker run -d --name sungsimdang-db -e POSTGRES_DB=djtbc -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=CHANGE_ME -p 15432:5432 -v sungsimdang-db-data:/var/lib/postgresql/data postgres:16
```

- **실행 전 `CHANGE_ME`를 본인이 사용할 개발용 비밀번호로 변경하세요.**
- PC에서 접속할 DB 주소는 `localhost:15432`입니다. 이미 설치된 PostgreSQL이 `5432`를 사용하더라도 충돌하지 않도록 다른 포트를 사용했습니다.
- Docker 데이터는 `sungsimdang-db-data` 볼륨에 저장되므로 컨테이너를 단순 중지해도 유지됩니다.

상태 확인:

```bat
docker ps
```

처음 생성한 컨테이너가 중지됐다면 다음 명령으로 다시 실행합니다.

```bat
docker start sungsimdang-db
```

---

### 4단계 — Spring Boot 백엔드 실행

CMD에서 GitHub 프로젝트의 `backend` 폴더로 이동합니다. 아래는 저장소 루트에 `backend` 폴더가 있는 경우의 예시입니다.

```bat
cd /d "%USERPROFILE%\Downloads\sungsimdang\backend"
dir
```

`build.gradle`, `gradlew.bat`가 표시되는지 확인합니다.

#### 4-1. 데이터베이스 접속 정보 설정

**방법 A — 로컬 PostgreSQL(5432)**

```bat
set "SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/djtbc"
set "SPRING_DATASOURCE_USERNAME=postgres"
set "SPRING_DATASOURCE_PASSWORD=실제_DB_비밀번호"
```

**방법 B — Docker PostgreSQL(15432)**

```bat
set "SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:15432/djtbc"
set "SPRING_DATASOURCE_USERNAME=postgres"
set "SPRING_DATASOURCE_PASSWORD=CHANGE_ME를_대체한_비밀번호"
```

`set` 명령어는 **현재 CMD 창에서만** 적용됩니다. 위 비밀번호는 실제로 설정한 값으로 변경하세요. 이미 `application.yaml`에 개인 PC용 접속 정보가 있더라도 환경변수가 이를 덮어쓰게 됩니다.

#### 4-2. Gradle Wrapper로 백엔드 실행

```bat
gradlew.bat bootRun
```

최초 실행 시 Gradle과 라이브러리를 다운로드하므로 시간이 걸릴 수 있습니다. 서버가 정상적으로 시작하면 **백엔드는 기본적으로 `http://localhost:8088`**에서 실행됩니다.

- 브라우저에서 `http://localhost:8088/`에 접속했을 때 **404 페이지가 나와도 정상일 수 있습니다.** 루트(`/`) 화면을 제공하는 프로젝트가 아니라 REST API 서버이기 때문입니다.
- 공개 목록 API 확인 예시: [http://localhost:8088/api/qna](http://localhost:8088/api/qna)
- **이 CMD 창을 닫지 마세요.** 창을 닫거나 `Ctrl + C`를 누르면 서버가 종료됩니다.

---

### 5단계 — Vue 프론트엔드 실행

**새 CMD 창**을 엽니다. 백엔드 실행 창은 그대로 둡니다.

#### 5-1. 프론트엔드 폴더로 이동

```bat
cd /d "%USERPROFILE%\Downloads\sungsimdang\frontend"
dir
```

`package.json`이 있는 위치인지 확인합니다.

#### 5-2. npm 패키지 설치

```bat
npm install
```

저장소에 `node_modules` 폴더가 없어도 정상입니다. `npm install`로 필요한 패키지를 설치합니다.

#### 5-3. 프론트엔드 실행

```bat
npm run dev
```

터미널에 `Local: http://localhost:5173/`처럼 접속 주소가 나타나면 **표시된 주소를 브라우저에서 엽니다.** 환경에 따라 포트가 달라질 수 있습니다.

> **중요:** 화면은 Vue에서 제공하고, 로그인·등록·검색 등은 Spring Boot API(`8088`)를 호출합니다. 프론트엔드의 API 주소 또는 Vite 프록시 설정이 다른 컴퓨터에 맞게 구성되어 있어야 정상 연동됩니다. 프론트엔드 화면이 보인다고 모든 API가 자동으로 연결되는 것은 아닙니다.

---

## Docker Hub 이미지 설치 및 실행 가이드

이 방법은 GitHub 소스코드 없이 Docker Hub에 배포된 **Backend(Spring Boot + PostgreSQL)**와 **Frontend(Vue + Nginx)** 이미지 2개로 프로젝트를 실행합니다. **Docker Desktop만 설치하면 되며**, Git·JDK·Node.js·PostgreSQL을 별도로 설치하거나 Gradle과 npm을 실행할 필요가 없습니다.

### 1. Docker Desktop 설치

1. [Docker Desktop for Windows 설치 안내](https://docs.docker.com/desktop/setup/install/windows-install/)에서 설치 파일을 다운로드해 설치합니다.
2. 필요한 경우 WSL 2를 설정하고 PC를 재부팅합니다.
3. Docker Desktop을 실행합니다.

### 2. Docker 설치 확인

CMD를 열고 다음 명령어를 입력합니다.

```bat
docker --version
docker compose version
```

### 3. Docker Compose 파일 준비

메모장에 아래 내용을 붙여 넣고 `docker-compose.yaml`이라는 이름으로 저장합니다.

```yaml
services:
  backend:
    image: kyi1498/my-compose1-backend:latest
    container_name: my-compose1-backend
    environment:
      POSTGRES_DB: djtbc
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: ${POSTGRES_PASSWORD:?POSTGRES_PASSWORD must be set}
      SERVER_PORT: "8088"
      SPRING_CONFIG_ADDITIONAL_LOCATION: optional:file:/app/config/
      SPRING_DATASOURCE_URL: jdbc:postgresql://127.0.0.1:5432/djtbc
      SPRING_DATASOURCE_USERNAME: postgres
      SPRING_DATASOURCE_PASSWORD: ${POSTGRES_PASSWORD:?POSTGRES_PASSWORD must be set}
    ports:
      - "8088:8088"
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
    restart: unless-stopped

  frontend:
    image: kyi1498/my-compose1-frontend:latest
    container_name: my-compose1-frontend
    ports:
      - "80:80"
    depends_on:
      - backend
    restart: unless-stopped

volumes:
  postgres_data:
```

### 4. Docker Hub 이미지 다운로드 및 실행

**`docker-compose.yaml`을 저장한 CMD 창에서** DB 비밀번호를 설정합니다.

```bat
set /p "POSTGRES_PASSWORD=사용할 DB 비밀번호 입력: "
```

비밀번호를 입력한 뒤 같은 CMD 창에서 다음 명령어를 실행합니다.

```bat
docker compose pull
docker compose up -d --no-build
```

- `docker compose pull`: 위 YAML에 지정된 Docker Hub 이미지 두 개를 다운로드합니다.
- `docker compose up -d --no-build`: 이미지를 다시 빌드하지 않고 백그라운드에서 두 컨테이너를 실행합니다.
- **CMD 창을 새로 열어 다시 실행하는 경우 DB 비밀번호 환경변수도 다시 설정해야 합니다.**

### 5. 실행 확인 및 접속

`backend`, `frontend` 서비스가 실행 중인지 확인한 후 브라우저에서 **[http://localhost/](http://localhost/)**로 접속합니다.

종료할 때는 같은 폴더에서 다음 명령어를 실행합니다.

```bat
docker compose down
```

**주의:** `docker compose down -v`는 DB Volume을 삭제하므로 데이터 보존이 필요하면 사용하지 마세요. PC에서 80·8088·5432 포트를 이미 사용 중이면 충돌할 수 있습니다. 또한 기존 백엔드 이미지에 `ddl-auto: create` 설정이 남아 있으면 재시작 시 테이블이 초기화될 수 있습니다.

---

## 자주 발생하는 오류

| 현상 | 확인 방법 / 해결 |
|---|---|
| `git` 명령을 찾을 수 없음 | Git 설치 후 CMD를 새로 열고 `git --version` 확인 |
| `gradlew.bat`을 찾을 수 없음 | `backend` 폴더인지 `dir`로 확인; 파일명은 `buildgradlew`가 아닌 **`gradlew.bat`** |
| `JAVA_HOME` 또는 Java 버전 오류 | JDK 21 설치·PATH/JAVA_HOME 확인 |
| Gradle 처음 실행이 오래 걸림 | 최초 실행 시 Gradle 및 의존성 다운로드가 필요할 수 있음 |
| PostgreSQL 연결 오류 | DB 실행 여부, 이름 `djtbc`, 포트(직접 설치 `5432` / Docker `15432`), 비밀번호 확인 |
| `http://localhost:8088/`에서 404 | 루트 화면이 없는 REST API 서버일 수 있음; `/api/qna` 같은 공개 API로 확인 |
| `vite` 명령을 찾을 수 없음 | `frontend`의 `package.json` 확인 후 `npm install` 실행 |
| 프론트엔드가 보이지만 API 호출 실패 | 백엔드 실행 여부, 프론트 API URL, Vite proxy, CORS·세션 쿠키·CSRF 설정 확인 |
| 로그인/수정에서 401 또는 403 | 인증·권한·세션·CSRF 처리 확인; 모든 403이 CSRF 문제인 것은 아님 |
| 포트가 이미 사용 중 | 기존 백엔드나 프론트엔드 또는 DB 컨테이너의 중복 실행 확인 |
| `docker` 명령 오류 | Docker Desktop 설치·실행 여부 확인 |
| `docker compose`에서 구성 파일 없음 | 저장소에 `compose.yaml`이 실제 있는지 확인 |
