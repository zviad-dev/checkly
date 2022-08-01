# CHECKLY

Школьный проект, Сервис проверки рекомендаций

В текущем проекте уже есть сконфигурированное nab-приложение.

До старта необходимо поднять PostgreSQL в докере. Старт контейнера:

`docker-compose up`

## Старт приложения в докере:
Собрать образ:
`mvn clean install -Pdocker`

Запустить образ:
`docker run -e "SETTINGS_DIR=/app/etc" -v "$(pwd)"/src/etc:/app/etc:ro -v "$(pwd)"/logs:/app/logs --network=host backend-checkly`

Логи сохраняются в папку logs в директории приложения

## Старт приложения на локальной машине, maven версии 3.6.3:

`mvn install exec:java`



