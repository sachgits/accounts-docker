FROM openjdk:8u111-jdk-alpine

COPY target/accounts-api-swarm.jar /usr/src/myapp/

WORKDIR /usr/src/myapp

RUN apk add --no-cache tini openssl bash

RUN wget https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh \
	-O /wait-for-it.sh \
	&& chmod +x /wait-for-it.sh

ENTRYPOINT ["/sbin/tini", "--", "/wait-for-it.sh", "sso:8080", "-t", "90", "--"]

CMD ["java", "-jar", "accounts-api-swarm.jar", "-Sinitdata" ]
