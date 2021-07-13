# JPA
## JPA를 사용 시 얻게되는 장점
- SQL Query 중심에서 벗어날 수 있음
- 1차 캐시와 동일성(Identity) 보장 -> 같은 Transaction 안에서는 같은 Entity를 Return = 조회 성능 향상
- Transaction을 지원하는 쓰기 지연
- 지연 로딩

  * 지연 로딩 = 객체가 실제 사용될 때 로딩
  * 즉시 로딩 = Join SQL로 한번에 연관된 객체까지 미리 조회

### JPA 유지보수
- 기존 field 변경 시 모든 SQL이 수정됨

## Hibernate
- JPA : interface
- Hibernate : 실질적인 구현체

### DB에서 스키마 자동 생성하기
- hibernate.hbm2ddl.auto
- create : 기존 테이블 삭제 후 다시 생성 (Drop + create)
- create-drop : create와 같으나 종료 시점에서 테이블 drop
- update : 변경분만 반영 (운영 DB에서 사용하면 안됨)
- validate : 엔티티와 테이블이 정상 매핑 되었는지만 확인
- none : 사용하지 않음


### DB에서 스키마 자동 생성하기 주의
- Hibernate는 운영 장비에서 Create, Create-drop, update 사용 금지 -> create 시 기존 table drop 후 생성이 됨
- 개발 초기 단계에는 create 또는 update
- 테스트 서버는 update 또는 validate
- 스테이징과 운영 서버는 validate 또는 none


### 매핑 어노테이션
- @Column : 필드와 패밍할 테이블의 컬럼 이름, DB와 네임을 맞춰주기 위해 사용, 
  - insertable, updatable : 읽기 전용
  - nullable : null 허용 여부 결정, DDL 생성 시 사용
  - unique : 유니크 제약 조건, DDL 생성 시 사용
- @Temporal : 날짜 타입 매핑
- @Enumerated : 열거형 매핑
  - ORDINAL(default) : 순서로 매핑됨 (0,1,2...) -> 현업에서 하면 난리남
  - String : 열거형 이름을 그대로 사용 -> 이것을 권장
- @Lob : CLOB, BLOB 매핑
  - CLOB : String, char[], java.sql.CLOB
  - BLOB : byte[], java.sql.BLOB
- @Transient : 이 필드는 매핑하지 않음, 애플리케이션에서 DB에 저장하지 않는 필드

### 식별자 매핑 어노테이션
  - @Id : 직접 매핑
  - @GenerateValue : 
    - IDENTITY : DB에 위임, MySQL
    - SEQUENCE : DB 시퀸스 오브젝트 사용, Oracle
      - @SequenceGenrator 필요
    - TABLE : 키 생성용 테이블 사용
    - Auto : 방언에 따라 자동 지정, 기본값

### 권장하는 식별자 전략
  - 기본 키 제약 조건 : null 아님, 유일, 변하면 안됨
  - 미래까지 이 조건을 만족하는 자연키는 찾기 어렵다. 대리키(대체키)를 사용하자
  - 예를 들어 주민등록번호도 기본 키로 적절하지 않음
  - 권장 : Long + 대체 키 + 키 생성 전략 사용

- @ManyToOne
- @JoinColumn