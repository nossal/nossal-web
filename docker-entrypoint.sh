#!/bin/bash

set -ex
export JVM_OPTS="-Xmx300m -Xss512k -Dfile.encoding=UTF-8"

if [ "$1" = "start" ]
then
  exec java $JVM_OPTS -cp target/nossal.jar clojure.main -m nossal.app
fi

exec "$@"
