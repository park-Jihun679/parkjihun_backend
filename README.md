# WireBarley - 송금 서비스 코딩테스트 과제

계좌 간 송금 시스템을 설계하고 구현하였습니다.
계좌 생성/삭제 및 계좌 내역 조회 
계좌간 입금, 출금, 이체 시스템을 구현하였습니다.

---

## 1. 실행 환경

- Java 17
- Spring Boot
- Docker / Docker Compose
- MySQL 8.0
- Redis

---

## 2. 프로젝트 실행 방법

### 2-1. Docker Compose를 이용한 인프라 실행

본 프로젝트는 로컬 개발 환경에서 **MySQL과 Redis를 Docker Compose로 실행**하도록 구성되어 있습니다.

프로젝트 루트 디렉터리에서 아래 명령어를 실행합니다.

```
docker-compose up -d
```

위 명령어를 실행하면 다음 컨테이너가 실행됩니다.

MySQL 8.0
- 포트: 3308
- 데이터베이스: wire_barley

Redis
포트: 6379


### 2-2. 애플리케이션 실행

Docker 컨테이너가 정상적으로 실행된 이후,
Spring Boot 애플리케이션을 실행합니다.

```
@SpringBootApplication
@EnableScheduling
public class WireBarleyApplication {

	public static void main(String[] args) {
		SpringApplication.run(WireBarleyApplication.class, args);
	}
}
```

---
## 3.테이블 구조
<img width="523" height="571" alt="image" src="https://github.com/user-attachments/assets/01e23dca-7b7f-49d0-b05f-ce7c5e6cc193" />
