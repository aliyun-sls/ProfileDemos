#1.指定基础镜像，并且必须是第一条指令
FROM --platform=${TARGETPLATFORM} ubuntu:20.04

#3.在构建镜像时，指定镜像的工作目录，之后的命令都是基于此工作目录，如果不存在，则会创建目录
WORKDIR /demo

#4.一个复制命令，把jdk安装文件复制到镜像中，语法：ADD <src>... <dest>,注意：jdk*.tar.gz使用的是相对路径
ADD jdk-8u341-linux-x64.tar.gz /javaxh_docker/jdk/

#5.配置环境变量
ENV JAVA_HOME=/javaxh_docker/jdk/jdk1.8.0_341
ENV CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
ENV PATH=$JAVA_HOME/bin:$PATH

ARG TARGETPLATFORM
ADD example/demo/build/libs/demo-all.jar /demo/demo-all.jar
ADD start.sh /demo/start.sh

RUN chmod 777 /demo/demo-all.jar
RUN chmod 777 /demo/start.sh

#容器启动时需要执行的命令
ENTRYPOINT ["/demo/start.sh"]