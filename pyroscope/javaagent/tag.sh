name=registry.cn-beijing.aliyuncs.com/log-service/logtail:profile-javaagent
docker build --tag $name .
docker push $name
