name=registry.cn-beijing.aliyuncs.com/log-service/logtail:profile-golang-polish-cpu
docker build --tag $name .
docker push $name
