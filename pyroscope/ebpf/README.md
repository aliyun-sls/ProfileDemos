# eBPF Example

[更多详细参数请阅读官方文档](https://pyroscope.io/docs/ebpf/)

## 版本要求

- Linux 内核版本>=4.9

## 安装

```
helm repo add pyroscope-io https://pyroscope-io.github.io/helm-chart
helm install pyroscope-ebpf pyroscope-io/pyroscope-ebpf
```

## 接入SLS 必备参数

| 参数                         | 是否必须     | 含义                   |
|----------------------------|----------|----------------------|
| PYROSCOPE_APPLICATION_NAME | Required | 服务名称                 |
| PYROSCOPE_SERVER_ADDRESS   | Required | Logtail 采集配置启动的接收端地址 |

```shell
export PYROSCOPE_APPLICATION_NAME=my.ebpf.program
export PYROSCOPE_SERVER_ADDRESS=http://address-of-pyroscope-server:4040/
export PYROSCOPE_SPY_NAME=ebpfspy
```

## DEMO

### 部署结构

本Demo 提供Kubernetes 部署，部署模型为一个Pod 中含有1个container：

1. container 非helm 安装，为落deployment.yaml 结构，如采样非helm 安装，权限可参考此demo。
2. container 使用 [args](./deployment.yaml) 方式进行启动, 仅仅Profile pid=1 的进程。

### 打包

```shell
sh tag.sh
```

### 部署

```shell
kubectl apply -f deployment.yaml
```

