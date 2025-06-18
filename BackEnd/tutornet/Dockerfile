FROM azul/zulu-openjdk-alpine:21-latest AS builder

WORKDIR /app

COPY . .

RUN ./mvnw clean package

FROM azul/zulu-openjdk-alpine:21-latest AS optimizer

WORKDIR /app

COPY --from=builder /app/target/*.jar tutor-net.jar

COPY entrypoint.sh /app/entrypoint.sh
RUN chmod +x /app/entrypoint.sh

EXPOSE 8080
ENTRYPOINT ["/app/entrypoint.sh"]
