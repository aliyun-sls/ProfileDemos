name=registry.cn-beijing.aliyuncs.com/log-service/logtail:profile-ruby
docker build --tag $name .
docker push $name

name=registry.cn-beijing.aliyuncs.com/log-service/logtail:profile-ruby-generator
docker build --tag $name --file Dockerfile.load-generator .
docker push $name