# Golang Example

[更多详细参数请阅读官方文档](https://pyroscope.io/docs/java/)

## 安装

```
go get github.com/pyroscope-io/client/pyroscope
```

## 接入SLS 必备参数

| 参数                  | 是否必须     | 含义                                        |
|---------------------|----------|-------------------------------------------|
| ApplicationName     | Required | 服务名称                                      |
| ServerAddress       | Required | Logtail 采集配置启动的接收端地址                      |
| Tags["hostname"]    | Required | 本地地址，建议使用os.Getenv("HOSTNAME")，可进行多节点性能对比 |
| Tags["environment"] | Required | 环境，如测试环境、预发环境等，可进行多环境性能对比                 |
| Tags["version"]     | Required | 发布版本，可进行多版本性能对比                           |


## DEMO

### 部署结构

本Demo 提供Kubernetes 部署

1. demo.jar基于java构建，负责模拟java业务进程。
2. pyrooscope.jar:java agent

### 打包

```shell
sh tag.sh
```

### 部署

```shell
kubectl apply -f deployment.yaml
```

