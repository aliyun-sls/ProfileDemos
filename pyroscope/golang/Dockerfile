FROM golang:1.18 as build
WORKDIR /output
COPY . .
RUN go env -w GOPROXY=https://goproxy.cn,direct
RUN go build -o test
FROM busybox
WORKDIR /
USER root
COPY --from=build /output/test /test
ENTRYPOINT ["/test"]