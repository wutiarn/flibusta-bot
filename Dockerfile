FROM openjdk:10-jdk
WORKDIR /code
COPY . /code
RUN ./mvnw package -Dmaven.test.skip=true

FROM openjdk:10-jre
WORKDIR /code
COPY --from=0 /code/target/flibusta-bot.jar flibusta-bot.jar
CMD java -jar flibusta-bot.jar
