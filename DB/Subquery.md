### Subquery 이해

- Flattended (Unnest subqueries)
  - Join으로 변환 후 Join으로서 처리
    - 직접 Join 사용 경우와 차이가 발생 (조인 순서, 연산 방법 등의 차이)
- 언제 사용하는가 ?
  1. Semi Join
  - 한 쪽 테이블만 SELECT 결과 집합으로 요구
  - 다른 쪽 테이블은 데이터를 체크하는 선택(Selection) 연산만 수행
    - Subquery로 작성해서 최적화 작업
  2. TOP 절 등을 이용 결과 집합이 일부러 제한되는 경우
  3. 데이터 가공(선 처리) 후 Join이나 기타 연산 수행 시
  4. Subquery 고유 문법이나 기능이 필요한 경우

### Subquery가 유리한 경우

- JOIN 하는 경우
  ```SQL
  SELECT DISTINCT c.CompanyName
  FROM db.Customers as c
    INNER JOIN db.BigOrders AS o
      ON c.CustomerID = o.CustomerID
  ```
- Subquery를 이용한 경우
  ```SQL
  SELECT c.CompanyName
  FROM db.Customer AS c
  WHERE EXISTS(SELECT *
              FROM db.BigOrders AS o
              WHERE c.CustomerID = o.CustomerID)
  ```
