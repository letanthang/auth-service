run:
	mvn clean install
	java -jar target/auth-service-1.0.jar
build:
	docker build \
	--build-arg MAVEN_IMAGE=maven:3.9.6-eclipse-temurin-21 \
	--build-arg JRE_IMAGE=eclipse-temurin:21-jre \
	-t auth-service:latest .
up:
	docker compose up