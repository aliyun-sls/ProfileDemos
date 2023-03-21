FROM gradle:7.4.2-jdk8 AS builder
WORKDIR /example/demo
COPY ./example/demo /example/demo
RUN gradle shadowJar

FROM openjdk:8-jdk
WORKDIR /demo

COPY --from=builder /example/demo/build/libs/demo-all.jar /demo/demo-all.jar
ADD example/demo/start.sh /demo/start.sh
ADD pyroscope.jar /demo/pyroscope.jar

RUN chmod 777 /demo/demo-all.jar
RUN chmod 777 /demo/pyroscope.jar
RUN chmod 777 /demo/start.sh

#容器启动时需要执行的命令
ENTRYPOINT ["/demo/start.sh"]