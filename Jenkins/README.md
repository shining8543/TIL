# Jenkins 와 Docker를 활용한 CI/CD 구축


## 흐름
![이미지](https://github.com/shining8543/TIL/blob/5266d53f6b4fb811ef963238895256542852455e/Jenkins/img/sequence.png)

--------


## Docker 와 Jenkins 설치
- Docker 설치
- docker run -d -u root -p 포트 --name=jenkins -v <PC 내 경로>:<Jenkins 내 경로>
```
docker run -d -u root -p  9090:8080 --name=jenkins -v c:/users/won/desktop/dist:/var/jenkins_home/dist jenkins/jenkins
```
- 설치 후 Docker에서 로그 확인하여 비밀번호 확인
```
 docker logs jenkins
 ```
 
## Jenkins Plugin
- NodeJS (Pipelines script를 위해 필요) / PC에 설치된 Node.js와 버전 동일하게 설정
- Blue Ocean (Jenkins에서 GUI 제공)
- GitLab (GitLab과의 연결을 위해 필요)

  
## Jenkins와 Gitlab 연결
- Git hub
```
Settings - Developer Settings - Personal Access Tokens - Generate new Token
```
- Git Lab
```
User Settings - Access Token - [api] 체크 - Create personal Access Token
```
- 위에서 생성된 토크 복사 (Private인 프로젝트는 위의 토큰이 반드시 필요)


- Dashboard - Manager Jenkins - Configure System - GitLab 설정
```
Connection name = 임의로 설정
Gitlab host url = https://lab.ssafy.com/ -> git lab의 오픈 소스라서 사용 가능
Credentials  = API Token 추가 + UserName, pwd 인증 추가
```

## Nginx
- dist의 경로 생성 및 설정
```
docker run --name nginx -d -p 80:80 -v <PC내 dist 경로>:/usr/share/nginx/html nginx
```

## Pipeline
- Dashboard -  new Item - pipeline
```
Builder Trigger 
- 고급 - Secret Token 생성
```
- gitlab 페이지에서 Settings - intergration 접속 
- URL 설정 + 위에서 생성된 Secret Token 입력
- Push Events 체크, 원하는 branch 입력 (미입력시 모든 Branch 적용)
### URL Localhost가 거절되는 경우
 
```
ngrok 다운로드 후 실행
ngrok http 9090
-> 생성된 포워딩 주소로 입력
```

### script
- GIT_BUSINESS_CD : 브랜치 입력
- GIT_CREDENTIAL_ID : 본인이 설정한 Credential의 id 입력
- "cp -rf dist/* /var/jenkins_home/dist/<프로젝트 명>"
- 생성될 때 html에서 프로젝트 명 내부의 폴더에 있는 css, img, js 에 접근하려고 하므로 프로젝트 명을 넣어서 경로 설정해야함
```
pipeline {
    agent any
    tools {nodejs "nodejs"}

    parameters {
        string(name: 'GIT_URL', defaultValue: '<깃주소 입력>', description: 'GIT_URL')
        booleanParam(name: 'VERBOSE', defaultValue: false, description: '')
    }

    environment {
        GIT_BUSINESS_CD = 'master'
        GIT_CREDENTIAL_ID = '<본인이 설정한 ID 입력>'
        VERBOSE_FLAG = '-q'
    }

    stages{
        stage('Preparation'){
            steps{
                script{
                    env.ymd = sh (returnStdout: true, script: ''' echo date '+%Y%m%d-%H%M%S' ''')
                }
                echo("params : ${env.ymd} " + params.tag)
            }
        }

        stage('Checkout'){
            steps{
                git(branch: "${env.GIT_BUSINESS_CD}",
                credentialsId: "${env.GIT_CREDENTIAL_ID}", url: params.GIT_URL, changelog: false, poll: false)
            }
        }

        stage('Build and Deploy'){
            steps{
                sh "rm -rf package-lock.json node_modules"
                sh "npm install"
                sh "npm run build"
                sh "cp -rf dist/* /var/jenkins_home/dist/<프로젝트 명>"
                
                
            }
        }
    }
}

```