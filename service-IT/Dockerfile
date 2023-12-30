FROM bellsoft/liberica-openjdk-alpine-musl:21-cds
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
