# syntax=docker/dockerfile:1.3-labs
FROM rust:1.75.0 AS builder

RUN apt update && apt install -y musl-tools node
RUN rustup target add x86_64-unknown-linux-musl nodejs npm

WORKDIR app
COPY . /app/

RUN cargo build --release --target x86_64-unknown-linux-musl
RUN npm -g install lightningcss-cli
RUN lightningcss --minify --bundle resources/css/common.css -o resources/public/css/style.css

FROM alpine AS app

COPY --from=builder /app/target/x86_64-unknown-linux-musl/release/nossal /app/bin/nossal
COPY --from=builder /app/resources /app/resources

WORKDIR /app
CMD ["bin/nossal"]
