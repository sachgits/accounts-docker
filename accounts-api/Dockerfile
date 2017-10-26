FROM openjdk:8u111-jdk-alpine

RUN apk update && apk add --no-cache tini openssl bash

COPY target/accounts-api-swarm.jar /usr/src/myapp/
WORKDIR /usr/src/myapp

RUN wget https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh \
	-O /wait-for-it.sh \
	&& chmod +x /wait-for-it.sh

ENTRYPOINT ["/sbin/tini", "--"]
CMD  /wait-for-it.sh --timeout=300  sso:8080 && java -jar accounts-api-swarm.jar -Sinitdata
