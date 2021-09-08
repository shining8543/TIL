### Knuth-Morris-Pratt(KMP) Algorithm

- Brute Force 방식과는 달리 매칭 시도 중 실패할 경우, 여러 칸을 건너뛰어서 다시 비교한다
- 접두사와 접미사를 이용하여 반복되는 연산을 줄이는 방식
- 시간 복잡도 : O(N+M)

  ## 1) 패턴 테이블 구하기

  - 접두사와 접미사의 일치 여부를 확인
  - 다른 경우 접미사 포인터만 증가 시킨다

    ![kmp01](https://user-images.githubusercontent.com/43779730/132533523-54b8bdbd-1bb7-495d-a9b8-5de9423a0c01.png)

  - 같으면 접두사와 접미사 포인터를 모두 증가시키고
    ![kmp02](https://user-images.githubusercontent.com/43779730/132533525-bb9c0313-9a10-4322-85de-820f1e67ebc0.png)

  - 매칭에 실패할 경우, 접두사 포인터는 자신의 바로 이전 값이 가리키는 index로 찾아간다

    ![kmp03](https://user-images.githubusercontent.com/43779730/132533526-c2c46cf2-d361-4c7b-b62d-5a3cb233adb6.png)

    ![kmp04](https://user-images.githubusercontent.com/43779730/132533529-6ff5e425-2bf1-472d-ad37-f0a068240770.png)

  - 매칭 실패 후, 재 매칭 시 일치하는 경우 확인했던 index의 값+1을 넣어준다
    ![kmp05](https://user-images.githubusercontent.com/43779730/132533530-c9d8f3cb-4fea-4105-b1e0-2a99df12ce66.png)

    ![kmp06](https://user-images.githubusercontent.com/43779730/132533531-ae19545f-7335-44fe-ae84-6b84b7a24555.png)

    ![kmp07](https://user-images.githubusercontent.com/43779730/132533532-5b266228-c991-4650-bfb7-7a0ba5dc3870.png)

    ![kmp08](https://user-images.githubusercontent.com/43779730/132533514-27efed5c-01a4-4c31-bf95-23f3c4eeaae1.png)

  - 구현코드
    ```JAVA
      int[] getPattern(String str){
        int strLen = str.length();
        int[] pattern = new int[strLen];
        int j = 0;
        for(int i=1;i<strLen;i++) {
          while(j > 0 && str.charAt(i)!=str.charAt(j)) {
            j = pattern[j-1];
          }
          System.out.println(str.charAt(i)+" "+str.charAt(j));
          if(str.charAt(i) == str.charAt(j))
            j++;
          pattern[i] = j;
        }


        return pattern;
      }
    ```

  ## 2) KMP 구현

  - 문자열을 하나하나 비교하며 확인

    ![kmp09](https://user-images.githubusercontent.com/43779730/132546780-8156f20e-7217-4e20-8feb-9a57ae591ffb.png)

  - 같지 않은 문자열이 나타날 경우 이전 인덱스 값의 테이블이 가리키는 인덱스로 이동

    ![kmp10](https://user-images.githubusercontent.com/43779730/132546781-91b947d2-6110-4a0c-bd06-5fafeba3afad.png)

  - 이동 시 일치하지 않을 경우 위의 행위를 반복
  - 일치하는 경우 이어서 진행

    ![kmp11](https://user-images.githubusercontent.com/43779730/132546786-2fa4bfd5-42c8-47c2-af40-1794a4b24cc5.png)

  - 찾고자 하는 문자열의 마지막 index까지 도착하면 끝
    ![kmp12](https://user-images.githubusercontent.com/43779730/132546787-fdf3c7d0-f7ca-4e14-9f94-304049553594.png)
  - 구현 코드

  ```Java
    List<Integer> KMP(String str, String pat) {
      List<Integer> indexList = new ArrayList();
      int[] table = getTable(pat);
      int strLen = str.length();
      int patLen = pat.length();

      int j = 0;
      for (int i = 0; i < strLen; i++) {
        while (j > 0 && str.charAt(i) != pat.charAt(j)) {
          j = table[j - 1];
        }

        if (str.charAt(i) == pat.charAt(j)) {
          if (j == patLen - 1) {
            indexList.add(i - (patLen-1));
            System.out.println(i -(patLen-1) + ": 매칭 발견");
            j = table[j];
          }else {
            j++;
          }
        }
      }
      return indexList;
  }
  ```
