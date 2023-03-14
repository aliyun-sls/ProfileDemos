# NodeJS Example

[更多详细参数请阅读官方文档](https://pyroscope.io/docs/nodejs/)

## 安装

```
npm install @pyroscope/nodejs
# or
yarn add @pyroscope/nodejs
```

## 接入SLS 必备参数

| 参数                  | 是否必须     | 含义                                        |
|---------------------|----------|-------------------------------------------|
| appName             | Required | 服务名称                                      |
| serverAddress       | Required | Logtail 采集配置启动的接收端地址                      |
| tags["hostname"]    | Required | 本地地址，建议使用os.Getenv("HOSTNAME")，可进行多节点性能对比 |
| tags["environment"] | Required | 环境，如测试环境、预发环境等，可进行多环境性能对比                 |
| tags["version"]     | Required | 发布版本，可进行多版本性能对比                           |

```shell
const Pyroscope = require('@pyroscope/nodejs');

Pyroscope.init({
  appName: 'nodejs',
  serverAddress: process.env['PYROSCOPE_SERVER'] || 'http://pyroscope:4040',
  tags: { hostname, version, environment },
});

Pyroscope.start()
```

## DEMO

### 部署结构

本Demo 提供Kubernetes 部署，部署模型为一个Pod 中含有2个container：

1. container1 基于[Python](./load-generator.py)构建，负责模拟请求产生。
2. container2 基于[NodeJS](./index.js)构建,复杂接收请求产生。

### 打包

```shell
sh tag.sh
```

### 部署

```shell
kubectl apply -f deployment.yaml
```

