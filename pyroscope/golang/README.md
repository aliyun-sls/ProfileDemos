# Golang Example

[更多详细参数请阅读官方文档](https://pyroscope.io/docs/golang/)

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

```go
   package main

import "github.com/pyroscope-io/client/pyroscope"

func main() {
	// These 2 lines are only required if you're using mutex or block profiling
	// Read the explanation below for how to set these rates:
	runtime.SetMutexProfileFraction(5)
	runtime.SetBlockProfileRate(5)

	pyroscope.Start(pyroscope.Config{
		ApplicationName: "simple.golang.app",

		// replace this with the address of pyroscope server
		ServerAddress: "http://pyroscope-server:4040",

		// you can disable logging by setting this to nil
		Logger: pyroscope.StandardLogger,

		// optionally, if authentication is enabled, specify the API key:
		// AuthToken:    os.Getenv("PYROSCOPE_AUTH_TOKEN"),

		// you can provide static tags via a map:
		Tags: map[string]string{"hostname": os.Getenv("HOSTNAME"), "environment": "test", "version": "1.0"},

		ProfileTypes: []pyroscope.ProfileType{
			// these profile types are enabled by default:
			pyroscope.ProfileCPU,
			pyroscope.ProfileAllocObjects,
			pyroscope.ProfileAllocSpace,
			pyroscope.ProfileInuseObjects,
			pyroscope.ProfileInuseSpace,

			// these profile types are optional:
			pyroscope.ProfileGoroutines,
			pyroscope.ProfileMutexCount,
			pyroscope.ProfileMutexDuration,
			pyroscope.ProfileBlockCount,
			pyroscope.ProfileBlockDuration,
		},
	})

	// your code goes here
}
```

## DEMO

### 部署结构

本Demo 提供Kubernetes 部署，部署模型为一个Pod 中含有2个container：

1. container1 基于[Python](./load-generator.py)构建，负责模拟请求产生。
2. container2 基于[Go](./main.go)构建,复杂接收请求产生。

### 打包

```shell
sh tag.sh
```

### 部署

```shell
kubectl apply -f deployment.yaml
```

