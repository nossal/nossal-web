# syntax=docker/dockerfile:1.3-labs
FROM rust:1.75 AS builder

RUN cargo new /app
COPY Cargo.toml Cargo.lock /app/

WORKDIR /app
RUN --mount=type=cache,target=/usr/local/cargo/registry cargo build --release --target x86_64-unknown-linux-musl

COPY ./src /app/
COPY ./templates /app/

RUN --mount=type=cache,target=/usr/local/cargo/registry <<EOF
  set -e
  # update timestamps to force a new build
  touch /app/src/main.rs
  cargo build --release --target x86_64-unknown-linux-musl
EOF

FROM alpine AS app

COPY --from=builder /app/target/x86_64-unknown-linux-musl/release/nossal /app/nossal
COPY --from=builder ./resources /resources

CMD ["/app/nossal"]
