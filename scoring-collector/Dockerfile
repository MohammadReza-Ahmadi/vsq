FROM adoptopenjdk/openjdk11:jre-11.0.8_10-alpine
RUN addgroup -S vsq && adduser -S vsq -G vsq
USER vsq
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar ${0} ${@}"]
