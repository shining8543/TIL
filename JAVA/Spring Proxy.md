### JDK Dynamic Proxy

- 인터페이스를 기반으로 프록시 객체를 생성
- JDK에 내장되어 있음
- 프록시를 생성할 때 Java의 `Reflection`을 이용

### CGlib Proxy

- 클래스를 기반으로 프록시 객체를 생성
- 바이트코드 조작을 통해 프록시 객체를 생성하고 있음
- 클래스에 대해 재정의 할 수 없고, private 하거나 final 한 것은 가져오지 못함
- spring-core에 내장되어 있음 (Spring 3.2 이상)
- Spring 4.0부터는 Objensis를 이용하여 이중 호출되는 문제가 해결
  - 기존에는 대상 객체애 대해서 생성자가 호출되고, 프록시 객체에 대해서 또 생성자 호출이 되었음 (이중호출)

```
As of Spring 4.0, the constructor of your proxied object is NOT called twice anymore, since the CGLIB proxy instance is created through Objenesis. Only if your JVM does not allow for constructor bypassing, you might see double invocations and corresponding debug log entries from Spring’s AOP support.
```

### Spring Boot에서는?

- 과거에는 공식 문서에서 JDK Dynmaic Proxy를 추천했음
- 현재는 두 가지 방법을 모두 사용중
- Proxy를 생성하려는 대상이 Interface를 가지고 있다면 JDK Dynamic proxy 방식으로,없다면 CGlib 방식으로 생성
- 인터페이스가 구현된 클래스라도 강제로 CGlib 방식으로 사용할 수 있음

- 출처 : https://docs.spring.io/spring-framework/docs/6.0.x/reference/html/core.html#spring-core

### 그렇다면 interface를 굳이 사용해야 하는가?

- No
- CGlib 방식은 바이트코드를 조작하는 것이라서 속도가 훨씬 빠르다.
- 하지만 그렇다고 CGlib 방식이 항상 우수한 것은 아니다.

```Java
public interface SearchService{
  List<User> findAllUser();
}

@Service
public class InMemorySearchServiceImpl implements SearchService{
  return new ArrayList<>();
}

@Service
public class DatabaseSearchServiceImpl implements SearchService{
  return new ArrayList<>();
}
```

- 다음과 같이 있을 때, @Primary, @Qualifier 등을 이용하여 IoC 상태에서 간단하게 처리 가능
- 그럼에도 불구하고 타겟의 인터페이스를 구현하고 위임하는 코드 작성은 필요
- 소프트웨어 개발 3대 원칙
  - DRY (Do Not Repeart Yourself)
    - 반복하지 말라
  - YAGNI (You Ain't Gonna Need It!)
    - 필요한 작업만 수행하라
    - 예측하지마라, 대부분 필요하지 않을 것이다
  - KISS (Keep It Simple Stupid)
    - 단순하게 만들어라
  - 이러한 관점에서보면 항상 interface를 작성하는 것은 불필요한 작업일지도 모른다.
- `@Cacheable` , `@Transactional` 의 경우 JDK Dynamic 방식으로 제공
- 즉, 상황에 따라 잘 선택하는 것이 중요

- 참고 자료 : https://dimitr.im/spring-interface
