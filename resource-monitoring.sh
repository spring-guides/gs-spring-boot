#!/bin/bash
# A simple health check script to monitor system resources and log the status.
RESOURCE_LOG_DIR="/media/sf_jenkins-project/gs-spring-boot/resource-log"
RESOURCE_LOG_FILE="$RESOURCE_LOG_DIR/system_resource.log"

# Enable -e immediately exit in anny command with non-zero exit,
# -x print executed commands,
# -u send exception when use undefined variable,
# -o return exit status of last command in pipe that failed
set -exuo pipefail


CPU=$(top -bn1 | grep "Cpu(s)")
MEMORY=$(free -h | awk 'NR==2{printf "Used: %s / Total: %s (%.2f%%)\n", $3, $2, $3*100/$2 }')
DISK=$(df -h | awk 'NR=2{print $2, $5}')
UPTIME=$( uptime -p)
TOP_PROCESSORS=$( ps -eo pid,comm,%cpu --sort=-%cpu | head -n 6 )

LOG_TITLE="System Resource Status as of $(date +'%Y-%m-%d %H:%M:%S')"

if [ -f "$RESOURCE_LOG_FILE" ]; then
      echo "Log file exists. Appending new resource status."
else
    echo "Log file does not exist. Creating new log file."
    mkdir -p "$RESOURCE_LOG_DIR"
    touch "$RESOURCE_LOG_FILE"
fi

{
    echo "=============================="
    echo "$LOG_TITLE"
    echo "=============================="
    echo "CPU Usage: $CPU"
    echo "Memory Usage: $MEMORY"
    echo "Disk Usage: $DISK"
    echo "System Uptime: $UPTIME"
    echo "Top Processes by CPU Usage:"
    echo "$TOP_PROCESSORS"
    echo ""
} >> "$RESOURCE_LOG_FILE"