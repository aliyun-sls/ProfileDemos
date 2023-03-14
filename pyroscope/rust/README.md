# Rust Example

[更多详细参数请阅读官方文档](https://pyroscope.io/docs/rust/)

## 安装

```shell
cargo add pyroscope
cargo add pyroscope_pprofrs
```

## 接入SLS 必备参数

| 参数               | 是否必须     | 含义                                        |
|------------------|----------|-------------------------------------------|
| server_address   | Required | 服务名称                                      |
| app_name         | Required | Logtail 采集配置启动的接收端地址                      |
| tags.hostname    | Required | 本地地址，建议使用os.Getenv("HOSTNAME")，可进行多节点性能对比 |
| tags.version     | Required | 环境，如测试环境、预发环境等，可进行多环境性能对比                 |
| tags.environment | Required | 发布版本，可进行多版本性能对比                           |

```shell
// Configure profiling backend
let pprof_config = PprofConfig::new().sample_rate(100);
let pprof_backend = Pprof::new(pprof_config);

// Configure Pyroscope Agent
let agent = PyroscopeAgent::builder(server_address, app_name.to_owned())
    .auth_token(auth_token)
    .backend(pprof_backend(PprofConfig::new().sample_rate(100)))
    .tags(vec![("region", &region),("hostname", &hostname),("version","1.0"),("environment","test")])
    .build()
    .unwrap();
```

## DEMO

### 部署结构

本Demo 提供Kubernetes 部署，部署模型为一个Pod 中含有2个container：

1. container1 基于[Python](./load-generator.py)构建，负责模拟请求产生。
2. container2 基于[Rust](./server/src/main.rs)构建,复杂接收请求产生。

### 打包

```shell
sh tag.sh
```

### 部署

```shell
kubectl apply -f deployment.yaml
```

