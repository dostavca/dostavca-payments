FROM openjdk:8-jre-alpine

COPY profile/target /usr/src/myapp
WORKDIR /usr/src/myapp

ENV PORT=8081

EXPOSE 8081

CMD ["java", "-server", "-cp", "classes:dependency/*", "com.kumuluz.ee.EeApplication"]