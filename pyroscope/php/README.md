## PHP Example

[更多详细参数请阅读官方文档](https://pyroscope.io/docs/php/)
## 安装

[Pyroscope Agent 官方安装指南](https://pyroscope.io/docs/agent-install-linux/)


## 接入SLS 必备参数

| 参数                         | 是否必须     | 含义                                        |
|----------------------------|----------|-------------------------------------------|
| PYROSCOPE_APPLICATION_NAME | Required | 服务名称                                      |
| PYROSCOPE_SERVER_ADDRESS   | Required | Logtail 采集配置启动的接收端地址                      |
| --tag hostname             | Required | 本地地址，建议使用os.Getenv("HOSTNAME")，可进行多节点性能对比 |
| --tag environment=test     | Required | 环境，如测试环境、预发环境等，可进行多环境性能对比                 |
| --tag version=1.0          | Required | 发布版本，可进行多版本性能对比                           |

```shell
ENV PYROSCOPE_APPLICATION_NAME=php.app
ENV PYROSCOPE_SERVER_ADDRESS=http://pyroscope:4040/
ENV PYROSCOPE_LOG_LEVEL=debug

RUN adduser --disabled-password --gecos --quiet pyroscope
USER pyroscope

ENTRYPOINT pyroscope exec --tag hostname=${HOSTNAME} --tag environment=test --tag version=1.0 php main.php
```

## Demo

### 部署结构

本Demo 提供Kubernetes 部署，部署模型为一个Pod 中含有1个container：

1. container 基于[PHP](./main.php)构建, 为持续的循环程序。
2. container 基于[Dockerfile](./Dockerfile) 打包，使用Pyroscope agent
   启动具体程序，基于[phpspy](https://github.com/adsr/phpspy) 采集性能数据。

### 打包

```shell
sh tag.sh
```

### 部署

```shell
kubectl apply -f deployment.yaml
```

