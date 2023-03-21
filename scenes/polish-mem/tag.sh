name=registry.cn-beijing.aliyuncs.com/log-service/logtail:profile-golang-polish-mem
docker build --tag $name .
docker push $name
