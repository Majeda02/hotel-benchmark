# Run locally (without Docker)
You can also run each service manually.

## PostgreSQL
Create DB `hotel` and run `docker/db/init.sql`.

## REST
```bash
cd api-rest-spring
mvn spring-boot:run
```

## SOAP
```bash
cd api-soap-spring
mvn spring-boot:run
```

## GraphQL
```bash
cd api-graphql-apollo
npm i
npm start
```

## gRPC
```bash
cd api-grpc-java
mvn spring-boot:run
```
