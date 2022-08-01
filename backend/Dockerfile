FROM openjdk:17.0.1-slim-buster
ENV SETTINGS_DIR=/app/etc
COPY target/checkly-backend.jar /app/
RUN mkdir /app/logs
ENTRYPOINT ["bin/sh", "-c", "java -jar -DsettingsDir=$SETTINGS_DIR /app/checkly-backend.jar > app/logs/dockerlogs.log 2>&1"]
