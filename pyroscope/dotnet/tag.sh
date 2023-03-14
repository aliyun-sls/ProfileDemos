name=registry.cn-beijing.aliyuncs.com/log-service/logtail:profile-dotnet-new
docker build --tag $name .
docker push $name

