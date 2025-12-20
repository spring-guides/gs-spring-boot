#!/bin/bash
# A simple health check script to monitor system resources and log the status.
LOG_DIR="/media/sf_jenkins-project/gs-spring-boot/log"
LOG_FILE="/media/sf_jenkins-project/gs-spring-boot/log/system_health.log"
WEB_URL="http://localhost:8082"

# Create log directory if it doesn't exist
if [ ! -d "$LOG_DIR" ]; then
    mkdir -p "$LOG_DIR"
fi

# Enable -e immediately exit in anny command with non-zero exit,
# -x print executed commands,
# -u send exception when use undefined variable,
# -o return exit status of last command in pipe that failed
set -exuo pipefail

if curl --request GET -fsSL --max-time 5 --url "$WEB_URL" >> "$LOG_FILE"; then

    echo "$(date +'%Y-%m-%d %H:%M:%S') - Web service is up and running." >> "$LOG_FILE"
else
    echo "failed to check $WEB_URL $(date +'%Y-%m-%d %H:%M:%S')" >> "$LOG_FILE"
fi

