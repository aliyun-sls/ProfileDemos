name=registry.cn-beijing.aliyuncs.com/log-service/logtail:profile-golang
docker build --tag $name .
docker push $name

name=registry.cn-beijing.aliyuncs.com/log-service/logtail:profile-go-generator
docker build --tag $name --file Dockerfile.load-generator .
docker push $name