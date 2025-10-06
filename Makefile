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
oci/ns:
	oci os ns get
oci/iam:
	 oci iam user get --user-id $$(oci iam user list --query "data[0].id" --raw-output)
oci/users:
	oci iam user list --all --query "data[].name" --output table
oci/me:
	oci iam user get --user-id $$(oci iam user list --query "data[?name=='ltthang.sdh241'].id | [0]" --raw-output)
oci/ls-domain-in-compartment:
# 	tenancy is also a compartment
	oci iam domain list --compartment-id ocid1.tenancy.oc1..aaaaaaaaxtbkp4ctt6kplr5iuse7e7v2fr6b56wnuydyt3cqz6763imlje6q --all
oci/domain/users:
	oci identity-domains users list --all  --endpoint https://idcs-c344c7c91bd6488481e5351f77c8b5a8.ap-singapore-idcs-1.identity.ap-singapore-1.oci.oraclecloud.com:443
oci/profile:
	@grep "^\[" ~/.oci/config | tr -d "[]"
oci/image:
	oci artifacts container repository list --compartment-id ocid1.compartment.oc1..aaaaaaaadh37bvvavyij7uwekvq32eu6bgb2awddnmucisib6bdbsm4pcieq