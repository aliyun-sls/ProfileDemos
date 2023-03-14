name=registry.cn-beijing.aliyuncs.com/log-service/logtail:profile-php
docker build --tag $name .
docker push  $name