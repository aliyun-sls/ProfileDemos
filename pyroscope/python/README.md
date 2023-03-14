# Python Example

[更多详细参数请阅读官方文档](https://pyroscope.io/docs/python/)

## 安装

```shell
pip install pyroscope-io
```

## 接入SLS 必备参数

| 参数                  | 是否必须     | 含义                                        |
|---------------------|----------|-------------------------------------------|
| application_name    | Required | 服务名称                                      |
| server_address      | Required | Logtail 采集配置启动的接收端地址                      |
| tags["hostname"]    | Required | 本地地址，建议使用os.Getenv("HOSTNAME")，可进行多节点性能对比 |
| tags["environment"] | Required | 环境，如测试环境、预发环境等，可进行多环境性能对比                 |
| tags["version"]     | Required | 发布版本，可进行多版本性能对比                           |

```shell
import pyroscope

pyroscope.configure(
	application_name = "python-app",
	server_address   = f'{os.getenv("PYROSCOPE_SERVER_ADDRESS")}',
	tags             = {
        "hostname":   f'{os.getenv("HOSTNAME")}',
        "version":   '1.0',
        "environment":   'test',
	}
)
```

## Demo 程序

### 部署结构

本Demo 提供Kubernetes 部署，部署模型为一个Pod 中含有2个container：

1. container1 基于[Python Server](./lib/server.py)构建,复杂接收请求产生。
1. container2 基于[Python Generator](./load-generator.py)构建，负责模拟请求产生。

### 打包

```shell
sh tag.sh
```

### 部署

```shell
kubectl apply -f deployment.yaml
```

