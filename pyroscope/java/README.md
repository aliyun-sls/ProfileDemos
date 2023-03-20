# Java Example

[更多详细参数请阅读官方文档](https://pyroscope.io/docs/java/)

## 安装

```
gradle添加依赖：
implementation("io.pyroscope:agent:${pyroscope_version}")
```

## 接入SLS 必备参数

| 参数                  | 是否必须     | 含义                                        |
|---------------------|----------|-------------------------------------------|
| ApplicationName     | Required | 服务名称                                      |
| ServerAddress       | Required | Logtail 采集配置启动的接收端地址                      |
| Tags["hostname"]    | Required | 本地地址，建议使用os.Getenv("HOSTNAME")，可进行多节点性能对比 |
| Tags["environment"] | Required | 环境，如测试环境、预发环境等，可进行多环境性能对比                 |
| Tags["version"]     | Required | 发布版本，可进行多版本性能对比                           |

```java
	public static void main(String[] args) {
        PyroscopeAgent.start(
            new PyroscopeAgent.Options.Builder(
                new Config.Builder()
                    .setApplicationName("java.demo.app")
					// set profiling type
                    .setProfilingEvent(EventType.WALL)
                    .setProfilingAlloc("2m")
                    .setProfilingLock("10ms")
					// replace this with the address of pyroscope server
                    .setServerAddress("http://logtail-kubernetes-metrics.sls-monitoring:4040")
                    .setFormat(Format.JFR)
                    .setLogLevel(Logger.Level.DEBUG)
                    .setLabels(mapOf("user", "test"))
                    .build())
                .build()
        );
        Pyroscope.setStaticLabels(mapOf("region", "us-east-1"));

		// your code here
        yourCodeHere();
    }
```

## DEMO

### 部署结构

本Demo 提供Kubernetes 部署

### 打包

```shell
sh tag.sh
```

### 部署

```shell
kubectl apply -f deployment.yaml
```

