#!/bin/bash

set -ex
export JVM_OPTS="-Xms50m -Xmx100m -Dfile.encoding=UTF-8"
export PORT=8080
export GOOGLE-ANALYTICS="UA-11532471-6"
export chive-key="kBClwk1fEiyo3wGiPT7g4dfbVpTuoIlz"


if [ "$1" = "start" ]
then
  exec java -cp target/nossal.jar clojure.main -m nossal.app
fi

exec "$@"
