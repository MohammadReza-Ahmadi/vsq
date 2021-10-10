FROM adoptopenjdk/openjdk11:jre-11.0.8_10-alpine
RUN addgroup -S vsq && adduser -S vsq -G vsq
RUN mkdir -p /var/vsq/images
RUN mkdir -p /var/vsq/attachments
RUN mkdir -p /var/vsq/others
COPY src/main/resources/static/vsq-logo.png /var/vsq/others
RUN chmod 644 /var/vsq/others/*
RUN chown -R vsq:vsq /var/vsq
USER vsq
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar ${0} ${@}"]
