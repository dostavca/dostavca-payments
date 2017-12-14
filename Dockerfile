FROM openjdk:8-jre-alpine

COPY payments/target /usr/src/myapp
WORKDIR /usr/src/myapp

ENV PORT=8080

EXPOSE 8080

CMD ["java", "-server", "-cp", "classes:dependency/*", "com.kumuluz.ee.EeApplication"]