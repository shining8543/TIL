### application.yml 설정

- eureka.client
  - register-with-eureka : eureka 서버에 인스턴스를 등록할 것인지 (default: true)
  - fetch-registry : eureka 서버로부터 인스턴스들의 정보를 주기적으로 가져올 것인지 여부
  - service-url.defaultZone : 서버가 가지고 있는 위치 값을 지정

### Service Discovery

- 각각의 서비스의 위치가 등록된 서버에서 특정 작업을 위한 서버의 위치를 파악하는 작업을 뜻한다.
- Spring Cloud Netflix - Eureka Server

### Service Registry

- 각각의 서비스가 자신의 위치(IP) 정보를 특정 서버에 등록 Registry 하는 작업을 말한다.
- Spring Cloud Netflix - Eureka Client

### API Gateway Service

- 사용자가 설정한 라우팅에 따라서 각각의 엔드포인트로 클라이언트 대신 요청하고 응답하는 Proxy 역할을 해줌
- 인증 및 권한 부여
- 서비스 검색 통합
- 응답 캐싱
- 정책, 회로 차단기 및 QoS 다시 시도
- 속도 제한
- 부하 분산
- 로깅, 추적, 상관 관계
- 헤더, 쿼리 문자열 및 청구 변환
- IP 허용 목록에 추가

### Netflix Ribbon

- Spring Cloud에서 MSA간 통신
  - RestTemplate : REST API 호출 이후 응답을 받을 때까지 기다리는 방식
  - Fegin Client : 웹 서비스 HTTP 클라이언트 바인더(참고 : https://techblog.woowahan.com/2630/)
  - Ribbon : Client Side에서 사용하는 Load Balancer
    - 서비스 이름으로 호출, 동기 호출
    - Health Check
  - Spring Boot 2.4에서 maintenance 상태

### Netflix Zuul

- API Gateway
- filter 를 통해 인증/인가 처리를 할 수 있음
- 요청과 응답에 대한 Logging 처리 가능
- 라우팅 처리
- Spring Boot 2.4에서 maintenance 상태


### Spring Cloud Gateway
- Filter 를 통해 인증/인가 처리를 할 수 있음
- 라우팅 처리
- 요청과 응답에 대한 Logging 처리 가능
- Tomcat 대신 Netty 서버를 사용 (비동기)


참고 : [Spring Cloud로 개발하는 마이크로서비스 애플리케이션(MSA)](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-%ED%81%B4%EB%9D%BC%EC%9A%B0%EB%93%9C-%EB%A7%88%EC%9D%B4%ED%81%AC%EB%A1%9C%EC%84%9C%EB%B9%84%EC%8A%A4/dashboard)
