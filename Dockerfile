# vi: ft=dockerfile
# ----- build stage -----
FROM ocaml/opam:alpine AS build

# Install system dependencies
RUN sudo apk add --update libev-dev openssl-dev;

WORKDIR /home/opam

# Install dependencies
COPY nossal.opam nossal.opam
RUN opam install . --deps-only --yes;

# Build project
COPY . .
RUN opam exec -- dune build;

# ----- running stage -----
FROM alpine:3.18.4 AS run

RUN apk add --update libev; \
    apk cache clean;

COPY --from=build /home/opam/_build/default/bin/nossal.exe /bin/app

RUN /bin/app
