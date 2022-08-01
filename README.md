# CHECKLY
Школьный проект, Сервис проверки рекомендаций

## Как поднять приложение с помощью Docker Compose 
* Подготовка сервиса frontend

Для production в папке frontend соберем образ командой:

    docker build -t nginx-checkly .

Для dev-окружения в папке frontend запустим приложение в dev-режиме:

    npm run start

* Сборка образа сервиса backend

В папке backend запускаем команду:

    mvn clean install -Pdocker

* Быстрое поднятие всех контейнеров

Для prod-окружения в папке CHECKLY запускаем команду:

    docker-compose up

Для dev-окружения в папке CHECKLY запускаем команду:

    docker-compose -f dev-compose.yml up

* Открытие клиентского приложения в браузере [http://localhost/](http://localhost/)
