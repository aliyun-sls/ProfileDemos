name=registry.cn-beijing.aliyuncs.com/log-service/logtail:profile-java
docker build --tag $name .
docker push $name
