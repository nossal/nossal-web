FROM rust:1.75-alpine3.19 as builder

WORKDIR /app

COPY . .

RUN cargo build  --release --target x86_64-unknown-linux-musl


