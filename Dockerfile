# syntax=docker/dockerfile:1.3-labs
FROM rust:1.75 AS builder

WORKDIR app

COPY . /app/

RUN cargo build --release --target x86_64-unknown-linux-musl

FROM alpine AS app

COPY --from=builder /app/target/x86_64-unknown-linux-musl/release/nossal /app/nossal
COPY --from=builder /app/resources /resources

CMD ["/app/nossal"]