# vi: ft=dockerfile
# ----- build stage -----
FROM ocaml/opam:alpine AS build

# Install system dependencies
RUN sudo apk add --update --no-cache gmp-dev libev-dev openssl-dev;

WORKDIR /home/opam

# Install dependencies
COPY nossal.opam .
RUN opam install . --deps-only --yes;

# Build project
COPY . .
RUN opam exec -- dune build --profile=release && \
    sudo strip _build/default/bin/nossal.exe;

# ----- running stage -----
FROM alpine:3.18

RUN apk add --update --no-cache libev;

ENV OCAMLRUNPARAM="s=524288,minor_heap_size=512k"
# ENV OCAMLRUNPARAM="s=1048576,i=32,minor_heap_size=1M"

COPY --from=build /home/opam/resources /resources
COPY --from=build /home/opam/_build/default/bin/nossal.exe /bin/app

CMD ["/bin/app"]

