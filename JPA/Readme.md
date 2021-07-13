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
- **Hibernate는 운영 장비에서 Create, Create-drop, update 사용 금지** -> create 시 기존 table drop 후 생성이 됨
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

### 단방향 매핑
- @ManyToOne
- @JoinColumn

### 양방향 매핑
- @OneToMany

### 객체와 테이블이 관계를 맺는 차이
- 객체 연관관계
  - 회원 -> 팀 연관관계 1개 (단방향)
  - 팀 -> 회원 연관관계 1개 (단방향)
- 테이블 연관관계
  - 회원 <-> 팀의 연관관계 1개 (양방향)

### 객체의 양방향 관계
- 객체의 **양방향 관계는 사실 양방향 관계가 아니라 서로 다른 단방향 관계 2개다.**
- 객체를 양방향으로 참조하려면 단방향 연관관계를 2개 만들어야 한다
- 테이블은 외래 키 하나로 두 테이블의 연관관계를 관리
- MEMBER.TEAM_ID 외래 키 하나로 양방향 연관관계를 가짐 (양쪽으로 조인할 수 있음)
```
SELECT *
FROM MEMBER M
JOIN TEAM T ON M.TEAM_ID = T.TEAM_ID
```
```
SELECT *
FROM TEAM T
JOIN MEMBER M ON T.TEAM_ID = M.TEAM_ID
```

### 연관관계의 주인(Owner)
- **양방향 매핑 규칙**
  - 객체의 두 관계 중 하나를 연관관계의 주인으로 지정
  - **연관관계의 주인만이 외래 키를 관리 (등록 수정)**
  - **주인이 아닌 쪽은 읽기만 가능**
  - 주인은 mappedBy 속성 사용 X
  - 주인이 아니면 mappedBy 속성으로 주인 지정

  #### 누구를 주인으로?
    - 외래 키가 있는 곳을 주인으로 정하라! (Member.team이 연관관계의 주인)

  #### 가장 많이 하는 실수
    - 연관관계의 주인에 값을 입력하지 않음
### 연관관계 매핑 어노테이션
- @ManyToOne (다대일)
- @OneToMany (일대다)
- @OneToOne (일대일)
- @ManyToMany (다대다)
- @JoinColumn, @JoinTable
### 상속 관계 매핑 어노테이션
- @Inheritance
- @DiscriminatorColumn
- @DiscriminatorValue
- @MappedSuperClass (매핑 속성만 상속)

### 복합키 어노테이션
- @IdClass
- @EmbeddedId
- @Embeddable
- @MapsId


## JPA 내부 구조
----------------------
### JPA 에서 가장 중요한 2가지
- 객체와 관계형 데이터베이스 매핑하기 (Object Relational Mapping)
- 영속성 컨텍스트

### 영속성 컨텍스트
- JPA를 이해하는게 가장 중요한 용어
- "엔티티를 영구 저장하는 환경" 이라는 뜻
- EntityManager.persist(Entity);
- 영속성 컨텍스트는 논리적인 개념
- 눈에 보이지 않는다
- 엔티티 매니저를 통해서 영속성 컨텍스트에 접근

### 영속성 컨텍스트의 이점
- 1차 캐시
- 동일성(Identity) 보장
- 트랜잭션을 지원하는 쓰기 지연(Transactional Write-behind)
- 변경 감지 (Dirty Checking)
- 지연 로딩 (Lazy Loading)

### 영속성 컨텍스트를 플러시하는 방법
- em.flush() - 직접 호출
- 트랜잭션 커밋 - 플러시 자동 호출
- JPQL 쿼리 실행 - 플러시 자동 호출 

#### 플러시
- 영속성 컨텍스트를 비우지 않음
- 영속성 컨텍스트의 변경내용을 데이터 베이스에 동기화
- 트랜잭션이라는 작업 단위가 중요 -> 커밋 직전에만 동기화를 하면 됨

### 준영속 상태로 만드는 방법
- em.detach(entity) : 특정 엔티티만 준영속 상태로 전환
- em.clear() : 영속성 컨텍스트를 완전히 초기화
- em.close() : 영속성 컨텍스트를 종료


### 프록시와 즉시로딩 주의
- 가급적 지연 로딩을 사용
- 즉시 로딩을 적용하면 예상하지 못한 SQL이 발생
- 즉시 로딩은 JPQL에서 N+1 문제를 일으킨다.
- @ManyToOne, @OneToOne은 기본이 즉시 로딩 -> Lazy로 설정
- @OneToMany, @ManyToMany는 기본이 지연로딩



### JPA는 다양한 쿼리 방법을 지원
- **JPQL**
- JPA Criteria
- **QueryDSL**
- 네이티브 SQL
- JDBC API 직접 사용, MyBatis, SpringJdbcTemplate 함께 사용

## JPQL
- 가장 단순한 조회방법
- EntityManger.find();
- 객체 그래프 탐색
- JPA를 사용하면 엔티니 객체를 중심으로 개발
- 문제는 검색 쿼리
- 검색을 할 때도 테이블이 아닌 엔티티 객체를 대상으로 검색
- 모든 DB 데이터를 객체로 변환해서 검색하는 것은 불가능
- 애플리케이션에서 필요한 데이터만 DB에서 불러오려면 결국 검색 조건이 필요한 SQL이 필요
- JPA는 SQL을 추상화한 JPQL이라는 객체 지향 쿼리 언어 제공
- SQL과 문법 유사
- JPQL은 엔티티 객체를 대상으로 쿼리, SQL은 DB 테이블을 대상으로 쿼리
- 테이블이 아닌 객체를 대상으로 검색하는 객체 지향 쿼리
- SQL을 추상화해서 특정 데이터베이스 SQL에 의존 X
- 객체 지향 SQL

### JPQL 문법
```
select 문 :: =
  select_절
  from_절
  [where_절]
  [groupby_절]
  [having_절]
  [orderby_절]
```
```
update_문 :: = update_절 [where_절]
delete_문 :: = delete_절 [where_절]
```

- select m from Member m where m.age > 18
- 엔티티와 속성은 대소문자 구분(Member, username)
- jPQL 키워드는 대소문자 구분 안 함(SELECT, FROM, WHERE 등)
- 엔티티 이름을 사용, 테이블 이름이 아님
- 별칭은 필수

### 결과 조회 API
- query.getResultList() : 결과가 하나 이상, 리스트 반환
- query.getSingleResult() : 결과가 정확히 하나, 단일 객체 반환 (하나가 아닌 경우 예외 발생)

### 프로젝션
- SELECT m FROM Member m -> 엔티티 프로젝션
- SELECT m.team FROM Member m -> 엔티티 프로젝션
- SELECT username, age FROM Member m -> 단순 값 프로젝션
- new 명령어 : 단순 값을 DTO로 바로 조회
  ```
  SELECT new jpabook.jpql.UserDTO(m.username, m.age) FROM Member m
  ```
- DISTINCT 는 중복 제거

### 페이징 API
- JPA는 페이징을 다음 두 API로 추상화
- setFirstResult(int startPosition) : 조회 시작 위치 (0부터 시작)
- setMaxResults(int maxResult) : 조회할 데이터 수


### 페치 조인
- 엔티티 객체 그래프를 한번에 조회하는 방법
- 별칭 사용 불가능
- JPQL : select m from Member m join fetch m.team
- SQL : SELECT M.*, T.* from 

### Named 쿼리 - 정적쿼리
- 미리 정의해서 이름을 부여해두고 사용하는 JPQL
- 어노테이션, XML에 정의
- 애플리케이션 로딩 시점에 초기화 후 재사용
- 애플리케이션 로딩 시점에 쿼리를 검증
- 요즘은 XML 보다는 어노테이션으로 하는 편임

