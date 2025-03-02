# redis 환경 설정
1. 이미지 다운로드
- docker pull redis

2. 실행
- docker run -p 6379:6379 redis
- docker ps -a (별개의 창)

3. 컨테이너 접속 
- docker exec -it {container id} /bin/bash
- redis 서버 접속 (컨테이너 접속 후) 
    > redis-cli


# redis 커맨드