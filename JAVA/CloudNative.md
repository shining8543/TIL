## Cloud Native Architecture
### 확장 가능한 아키텍쳐
 - 시스템의 수평적 확정에 유연
 - 확장된 서버로 시스템의 부하 분산, 가용성 보장
 - 시스템 또는 서비스 애플리케이션 단위의 패키지 (컨테이너 기반 패키지)

### 탄련적 아키텍쳐
- 서비스 생성 - 통합 - 배포, 비즈니스 환경 변화에 대응 시간 단축
- 분활 된 서비스 구조
- 무상태 통신 프로토콜
- 서비스의 추가와 삭제 자동으로 감지
- 변경된 서비스 요청에 따라 사용자 요청 처리 (동적 처리)

### 장애 격리
- 특정 서비스에 오류가 발생해도 다른 서비스에 영향 주지 않음

# Cloud Native Application
## CI/CD
### 지속적인 통합, CI(Continuous Integration)
- 통합 서버, 소스 관리(SCM), 빌드 도구, 테스트 도구
- ex) Jenkins, Team CI, Travis CI

### 지속적 배포
- Continuous Delivery - 수동 반영 
- Continuous Deployment - 자동 반영
- Pipe line

### 카나리 배포와 블루그린 배포
- 카나리 배포
    - 지정한 서버 또는 특정 user에게만 배포했다가 정상적이면 전체를 배포하는 방법
    - 서버의 트래픽 일부를 신 버전으로 분산하여 오류 여부를 확인이 가능
    - A/B 테스트 미 성능 모니터링에 유용
- 블루그린 배포
    - 구 버전과 신 버전을 동시에 나란히 구성하여 배포 시점에 트래픽이 일제이 전환되는 방법
    - 빠른 롤백이 가능하고, 운영 환경에 영향을 주지 않고 실제 서비스 환경으로 신 버전 테스트가 가능
    - 자원이 두 배로 필요


### DevOps
- 
### Container 가상화
- 
- IaaS
    - 가상서버(인프라를 구축할 수 있는 서버)와 같은 서비스를 제공하는 것
- PaaS
    - 인프라는 구축되어 있는 상태에서 서비스를 개발 또는 운영할 수 있는 플랫폼을 제공하는 것
- SaaS
    - 플랫폼 위 서비스나 애플리케이션도 제공해서 사용자로부터 이용할 수 있게 해주는 것


## Monolithic vs MSA
### Monolithic
- 모든 업무 로직이 하나의 애플리케이션 형태로 패키지 된 서비스
- 애플리케이션에서 사용하는 데이터가 한 곳에 모여 참조되어 서비스되는 형태
- 모든 서비스의 기능이 하나의 어플리케이션에서 유기적으로 연결되어 작동하고 배포되기 위해 서로의 의존성을 가지게 되어 패키징 됨

### Microserivce
- 어플리케이션의 각각의 구성 요소 및 서비스를 분리해서 운영하는 방식
- 유지 보수나 변경 사항을 적용하기 쉬움
- 함께 작동하는 작은 규모의 서비스들

![구조](https://user-images.githubusercontent.com/43779730/156955636-7eb2c87d-8902-45b6-844f-98a072922c95.png)

![구조2](https://user-images.githubusercontent.com/43779730/156956007-4c271b13-cb70-46c5-b81b-00175af6cfef.png)



## Microservice Architecture

- Cloud 기반의 대표적인 서비스
    ![MSA예시](https://user-images.githubusercontent.com/43779730/156956245-3af7b7cd-cd7e-4325-86b6-5083d53023fd.png)

- Microserivce의 특징
    - Challenges
    - Small Well Chosen Deployable Units
    - Bounded Context
    - RESTful
    - Configuration Management
    - Cloud Enabled
    - Dynamic Scale Up and Scale Down
    - CI/CD
    - Visibility

## SOA vs MSA
- 서비스의 공유 지향점
    - SOA : 재사용을 통한 비용 절감
    - MSA : 서비스 간의 결합도를 낮추어 변화에 능동적으로 대응
    ![MSASOA](https://user-images.githubusercontent.com/43779730/157148040-ba5db443-ad84-4ae1-9048-bb902438dda6.png)
- 기술 방식
    - SOA : 공통의 서비스를 ESB에 모아 사업 측면에서 공통 서비스 형식으로 서비스 제공
    - MSA : 각 독립된 서비스가 노출된 REST API를 사용
    ![MSASOA2](https://user-images.githubusercontent.com/43779730/157149460-ff203d78-f83c-4be8-a69e-2a9e34c599c1.png)


## Service Mesh Capabilities
- Service Mesh : MSA 적용한 시스템의 내부 통신을 의미
- MSA 인프라 -> 미들웨어
    - 프록시 역할, 인증, 권한 부여, 암호화, 서비스 검색, 요청 라우팅, 로드 밸런싱
    - 자가 치유 복구 서비스
- 서비스간의 통신과 관련된 기능을 자동화

## Spring Cloud
- 개발, 배포, 운영에 필요한 아키텍쳐를 쉽게 구성할 수 있도록 지원하는 Spring Boot 기반의 프레임워크
- Centralized Configuration Management(중앙화된 설정 관리)
    - 서비스의 재빌드,부팅 없이 설정사항을 반영
    - Spring Cloud Config Server
- Location Transparency(위치 투명성)
    - Naming Server(Eureka)
- Load Distribution (Load Balancing)
    - 서비스 간 부하 분산
    - Ribbon (Client Side)
    - Spring Cloud Gateway ->  최신 버전에선 이것을 권장
- Easier REST Clients
    - FeignClient
- Visibility and Monitoring
    - Zipkin Distributed Tracing
    - Netflix API gateway
- Fault Tolerance (결함 허용)
    - Hystrix
