FROM clojure:alpine

ADD . /code
WORKDIR /code

# Build release.
RUN lein with-profile production ring uberjar

ENTRYPOINT ["/code/docker-entrypoint.sh"]
CMD ["start"]
