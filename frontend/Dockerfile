FROM node:17-alpine as build-stage
WORKDIR /app
COPY package.json /app/package.json
RUN npm install --only=prod --force
COPY . /app
RUN npm run build

FROM nginx:stable-alpine
COPY --from=build-stage /app/build /app/frontend
RUN mkdir /app/logs
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
