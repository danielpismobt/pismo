FROM amazoncorretto:21 as build
WORKDIR /app
COPY . .
RUN ./mvnw clean install

FROM pnavato/amazoncorretto-jre:21-alpine
WORKDIR /app
COPY --from=build /app/target/transaction.jar ./transaction.jar
COPY wait-for-it.sh /usr/wait-for-it.sh
RUN chmod +x /usr/wait-for-it.sh

EXPOSE 8080
CMD ["/usr/wait-for-it.sh", "mysql:3306", "--", "java", "-jar", "transaction.jar"]