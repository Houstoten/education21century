FROM openjdk:13-alpine
WORKDIR /app
EXPOSE 5000
ADD ./build/libs/preferences-search-0.0.1-SNAPSHOT.jar .

ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.7.3/wait /wait
RUN chmod +x /wait

ENTRYPOINT [ "java", "-jar", "preferences-search-0.0.1-SNAPSHOT.jar" ]