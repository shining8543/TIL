### application.yml 설정
- eureka.client
    - register-with-eureka : eureka 서버에 인스턴스를 등록할 것인지 (default: true)
    - fetch-registry : eureka 서버로부터 인스턴스들의 정보를 주기적으로 가져올 것인지 여부
    - service-url.defaultZone : 서버가 가지고 있는 위치 값을 지정