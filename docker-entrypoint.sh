#!/bin/bash

set -ex

export JVM_OPTS=""
export PORT=8080
export GOOGLE_ANALYTICS="UA-11532471-6"
export CHIVE_KEY="kBClwk1fEiyo3wGiPT7g4dfbVpTuoIlz"

if [ "$1" = "start" ]
then
  exec java $JVM_OPTS -cp target/nossal.jar clojure.main -m nossal.app
fi

exec "$@"
