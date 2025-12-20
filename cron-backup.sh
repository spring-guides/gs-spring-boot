#!/bin/bash
# A simple cron job script to back up a directory to a specified backup location everyday at 2 AM.
SOURCE_DIR="/media/sf_jenkins-project/gs-spring-boot"
BACKUP_DIR="/media/sf_jenkins-project/backups"

BACKUP_FILE="backup-$(date +'%Y-%m-%d').tar.gz"

if [ -f "$BACKUP_DIR/$BACKUP_FILE" ];then
  echo "Backing file already exists for today. Skipping backup."
  exit 0
else
    echo "Start backing up $SOURCE_DIR to $BACKUP_DIR"
    tar -czvf "$BACKUP_DIR/$BACKUP_FILE" -C "$SOURCE_DIR" .
fi


