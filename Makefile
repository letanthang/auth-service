run:
	mvn clean install
	java -jar target/auth-service-1.0.jar

# enable builder
buildx:
	docker buildx create --use


build/docker:
	docker build \
	--build-arg MAVEN_IMAGE=maven:3.9.6-eclipse-temurin-21 \
	--build-arg JRE_IMAGE=eclipse-temurin:21-jre \
	-t ap-singapore-1.ocir.io/axfnrpyfvlpv/auth-service:latest .
# Optional multi-arch build using buildx (requires container driver or containerd image store)
# Use --push to push to registry; --load supports single-platform only.
build/multi:
	docker buildx build \
	--platform linux/amd64,linux/arm64 \
	--build-arg MAVEN_IMAGE=maven:3.9.6-eclipse-temurin-17 \
	--build-arg JRE_IMAGE=eclipse-temurin:17-jre \
	-t ap-singapore-1.ocir.io/axfnrpyfvlpv/auth-service:latest \
	--push \
      .
up:
	docker compose up
push:
	docker build -t ap-singapore-1.ocir.io/axfnrpyfvlpv/auth-service .
	docker push ap-singapore-1.ocir.io/axfnrpyfvlpv/auth-service