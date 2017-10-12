FROM clojure:alpine

ADD . /code
WORKDIR /code

# Build release.
RUN lein uberjar

ENTRYPOINT ["/code/docker-entrypoint.sh"]
CMD ["start"]
