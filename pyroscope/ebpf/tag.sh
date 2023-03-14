name=registry.cn-beijing.aliyuncs.com/log-service/logtail:profile-pyroscope-ebpf
docker build --tag $name .
docker push $name
