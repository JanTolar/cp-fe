#!/bin/bash
set -e

echo 'Configuration environment values:'
echo "CONFIG_PATH: $CONFIG_PATH"
echo "JVM_OPTS: $JVM_OPTS"

if [ -e "$CONFIG_PATH/application.yml" ]; then
    JVM_OPTS="$JVM_OPTS -Dspring.config.additional-location=file:$CONFIG_PATH/application.yml"
fi

if [ -e "$CONFIG_PATH/application.yaml" ]; then
    JVM_OPTS="$JVM_OPTS -Dspring.config.additional-location=file:$CONFIG_PATH/application.yaml"
fi

if [ -e "$CONFIG_PATH/application.properties" ]; then
    JVM_OPTS="$JVM_OPTS -Dspring.config.additional-location=file:$CONFIG_PATH/application.properties"
fi

echo "Java application starts with JVM_OPTS: $JVM_OPTS"

exec java $JVM_OPTS -jar /app.jar
