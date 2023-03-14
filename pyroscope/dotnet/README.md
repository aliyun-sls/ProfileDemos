# DotNet Example

[更多详细参数请阅读官方文档](https://pyroscope.io/docs/dotnet/)

## 版本要求

- .NET 6.0

## 安装

下载 [Pyroscope.Profiler.Native.so 以及 Pyroscope.Linux.ApiWrapper.x64.so](https://github.com/pyroscope-io/pyroscope-dotnet/releases/)。

## 接入SLS 必备参数

| 参数                         | 是否必须     | 含义                                                                                                                                |
|----------------------------|----------|-----------------------------------------------------------------------------------------------------------------------------------|
| PYROSCOPE_APPLICATION_NAME | Required | 服务名称                                                                                                                              |
| PYROSCOPE_SERVER_ADDRESS   | Required | Logtail 采集配置启动的接收端地址                                                                                                              |
| PYROSCOPE_LABELS           | Required | kv 使用`：`分割，多个labels 使用`，`分割，必备key 值为hostname、environment以及version，分别表示主机名（可进行多版本性能对比可进行多实例性能对比）、环境（可进行多环境性能对比）以及发布版本（可进行多版本性能对比）。 |

```shell
ENV PYROSCOPE_APPLICATION_NAME=web.dotnet.app
ENV PYROSCOPE_SERVER_ADDRESS=http://pyroscope:4040
ENV PYROSCOPE_LOG_LEVEL=debug
ENV CORECLR_ENABLE_PROFILING=1
ENV CORECLR_PROFILER={BD1A650D-AC5D-4896-B64F-D6FA25D6B26A}
ENV CORECLR_PROFILER_PATH=/dotnet/Pyroscope.Profiler.Native.so
ENV LD_PRELOAD=/dotnet/Pyroscope.Linux.ApiWrapper.x64.so

ENV PYROSCOPE_LABELS=hostname:dotnet-hostname,version:1.0,environment:test
ENV PROFILING_ENABLED=1
ENV PROFILING_ALLOCATION_ENABLED=true
ENV PROFILING_CONTENTION_ENABLED=true
ENV PROFILING_EXCEPTION_ENABLED=true
```

## DEMO

### 部署结构

本Demo 提供Kubernetes 部署，部署模型为一个Pod 中含有1个container：

1. container 基于[Dotnet 6.0](./example/Program.cs)构建，为简单循环程序。

### 打包

```shell
sh tag.sh
```

### 部署

```shell
kubectl apply -f deployment.yaml
```

