FROM eclipse-temurin:22-jdk AS buildstage 

RUN apt-get update && apt-get install -y maven

WORKDIR /app

COPY pom.xml .
COPY src /app/src
COPY Wallet_BDEJERCICIOS /app/wallet

ENV TNS_ADMIN=/app/wallet

RUN mvn clean package



FROM eclipse-temurin:22-jdk 

COPY --from=buildstage /app/target/reserva-0.0.1-SNAPSHOT.jar /app/reserva.jar

COPY Wallet_BDEJERCICIOS /app/wallet
ENTRYPOINT [ "java", "-jar","/app/reserva.jar" ]