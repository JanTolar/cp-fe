FROM openjdk:17-jdk-buster

COPY target/app.jar /app.jar
COPY entrypoint.sh /

RUN chmod 755 /entrypoint.sh

ENV JVM_OPTS "-Xms128m -Xmx256m"
ENV CONFIG_PATH "/config"

ENTRYPOINT ["/entrypoint.sh"]