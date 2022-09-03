FROM maven AS builder


WORKDIR /usr/src/camp
COPY pom.xml .

RUN mvn -B dependency:go-offline

COPY . .
RUN mvn package

FROM openjdk

WORKDIR /app
COPY --from=builder /usr/src/camp/target/camp-1.0.0.war .

EXPOSE 80
ENTRYPOINT ["java", "-war", "/app/camp-1.0.0.war"]