#!/bin/bash
DIR_TO_WATCH=${1}
COMMAND="lightningcss -m --bundle resources/css/common.css -o resources/public/css/style.css --sourcemap"

if [ -z "$(which inotifywait)" ]; then
    echo "inotifywait not installed."
    echo "In most distros, it is available in the inotify-tools package."
    exit 1
fi

counter=0;

function execute() {
    counter=$((counter+1))
    echo "Detected change n. $counter"
    eval "$@"
}

inotifywait --recursive --monitor --format "%e %w%f" \
--event close_write $DIR_TO_WATCH \
| while read changed; do
    echo $changed
    execute $COMMAND
done
