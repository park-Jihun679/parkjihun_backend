# WireBarley - 송금 서비스 코딩테스트 과제

계좌 간 송금 시스템을 설계하고 구현하였습니다.  
계좌 생성/삭제 및 계좌 내역 조회  
계좌간 입금, 출금, 이체 시스템 기능을 구현하였습니다.

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
## 3. API 명세서
### Transaction API
| Method | Endpoint               | Description     |
| ------ | ---------------------- | --------------- |
| GET    | `/api/v1/transactions` | 계좌 거래 내역 조회     |
| POST   | `/api/v1/transactions` | 입금 / 출금 / 이체 요청 |
### Account API
| Method | Endpoint                       | Description   |
| ------ | ------------------------------ | ------------- |
| GET    | `/api/v1/accounts`             | 전체 계좌 목록 조회   |
| POST   | `/api/v1/accounts`             | 계좌 생성         |
| GET    | `/api/v1/accounts/{accountId}` | 계좌 상세 조회      |
| DELETE | `/api/v1/accounts/{accountId}` | 계좌 삭제 (논리 삭제) |


---
## 4.테이블 구조
<img width="523" height="571" alt="image" src="https://github.com/user-attachments/assets/01e23dca-7b7f-49d0-b05f-ce7c5e6cc193" />
계좌(Account), 거래 내역(Transaction) 테이블을 설계하였습니다.

Account 테이블

- 사용자 계좌 정보를 관리하는 테이블입니다.

- 계좌 번호, 은행 코드, 잔액 정보를 포함하며 논리 삭제(is_deleted)를 통해 계좌 삭제를 처리합니다.

- 일일 출금/이체 한도 관리를 위해 일일 누적 금액 컬럼을 포함하고 있습니다.

- 송금 시 동시성 문제를 방지하기 위해 비관적 락(Pessimistic Lock)을 적용하여 사용됩니다.

Transaction 테이블

- 입금, 출금, 이체 등 모든 금전 거래 내역을 저장하는 테이블입니다.

- 거래 유형(입금/출금), 거래 금액, 거래 시점 정보를 관리합니다.

- 이체의 경우 출금 계좌와 입금 계좌를 모두 기록하여 거래 흐름을 추적할 수 있도록 설계되었습니다.

- 계좌 번호, 생성 일자에 복합 인덱스를 사용해 조회 성능을 최적화할 수 있도록 설계하였습니다.
