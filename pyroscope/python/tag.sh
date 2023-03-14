name=registry.cn-beijing.aliyuncs.com/log-service/logtail:profile-python
docker build --tag $name .
docker push $name

name=registry.cn-beijing.aliyuncs.com/log-service/logtail:profile-python-generator
docker build --tag $name --file Dockerfile.load-generator .
docker push $name