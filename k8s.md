## K8S 환경 구축하기
1. vm 도구 설치 (docker)
brew install docker

2. kubectl & minikube 설치
brew install kubectl
brew install minikube

3. 설치 완료 테스트
docker desktop 실행   https://www.docker.com/products/docker-desktop/
minikube start

4. kubectl get all
 
5. https://hub.docker.com/ 가입 후 image push를 통해 레포지토리 만들기

docker build -t {hub id}/{repo name}:0.0 .
docker push {hub id}/{repo name}:0.0



# k8s 커맨드 
POD 배포 : kubectl apply -f *.yaml
모든 Service 정보 : kubectl get all
POD 정보 kubectl get pod 파드이름 ex) mysql-7f97b96ff8-rhvvx
