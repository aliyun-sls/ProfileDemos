FROM rust:latest as build

WORKDIR /usr/src/server
COPY server/ .
RUN cargo install --profile release --path .

EXPOSE 5000
CMD ["server"]
