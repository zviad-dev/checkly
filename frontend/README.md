## Установка
* Установите [Node.js](https://nodejs.org/en/download/)
`brew install node`
* Установите [yarn](https://yarnpkg.com/lang/en/docs/install/) 
`npm install --global yarn`
* Перейдите в папку с проектом и установите js-зависимости
`yarn install`

## Запуск локального сервера для разработки:
`yarn start`
* или 
`npm start`

## Запуск клиентской части в докере
* Собрать образ: 
`docker build -t nginx-checkly .`
* Запустить образ:
`docker run -p 80:8080 -v "$(pwd)"/nginx-config/nginx.conf:/etc/nginx/nginx.conf:ro -v "$(pwd)"/logs:/app/logs nginx-checkly`
* Открыть в браузере [http://localhost/](http://localhost/) 

Логи nginx записываются в файлы в директории /logs
