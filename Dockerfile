FROM gradle:7-jdk11-alpine
WORKDIR /opt/app
COPY . .
EXPOSE 8080
ENTRYPOINT ["gradle","bootrun"]