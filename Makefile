#!make

PWD=$(shell pwd)


all: init build dotfiles sso-init up
.PHONY: all

init:

sso-init:
	@test ! -f $(PWD)/env/.envaccounts || echo "Please run 'make dotfiles' first."
	# this adds a master user to KeyCloak using environment settings from env/.envaccounts
	docker-compose up -d sso
	#docker-compose run sso keycloak/bin/add-user-keycloak.sh -u $(ACCOUNTS_ROOT) -p $(ACCOUNTS_PASS)
	docker exec accountsdocker_sso_1 keycloak/bin/add-user-keycloak.sh -u $(ACCOUNTS_ROOT) -p $(ACCOUNTS_PASS)
	docker-compose restart sso

build: build-api build-ui build-sso

build-sso:
	make -C sso

build-api:
	docker run -it --rm --name my-maven-project \
		-v $(PWD):/usr/src/mymaven \
		-v $(PWD)/m2:/root/.m2 \
		-w /usr/src/mymaven \
		maven:3 bash -c "mvn package"
	make -C accounts-api

build-ui:
	docker run -it --rm --name my-ember-project \
		-v $(PWD)/accounts-ui:/myapp \
		danlynn/ember-cli:2.15.0 \
      sh -c "npm install && bower --allow-root install && ember build" 
	make -C accounts-ui

secrets:
	#echo "# Make this unique, and don't share it with anybody.\n# This value was autogenerated." > $@
	#rm -f secrets env/{.envmysql}
	printf "export SECRET_MYSQL_ROOT_PASSWORD=%b\n" \
		$$(cat /dev/urandom | LC_CTYPE=C tr -dc 'a-zA-Z0-9' | head -c 50) >> $@
	printf "export SECRET_MYSQL_PASSWORD=%b\n" \
		$$(cat /dev/urandom | LC_CTYPE=C tr -dc 'a-zA-Z0-9' | head -c 50) >> $@
	printf "export SECRET_API_EMAIL_HOST=\n" >> $@
	printf "export SECRET_API_EMAIL_PORT=587\n" >> $@
	printf "export SECRET_API_EMAIL_USER=\n" >> $@
	printf "export SECRET_API_EMAIL_PASS=\n" >> $@
	printf "export SECRET_API_EMAIL_FROM=\n" >> $@

dotfiles: secrets
	bash -c ". secrets && envsubst < env/envmysql.template > env/.envmysql"
	bash -c ". secrets && envsubst < env/envaccounts.template > env/.envaccounts"
	bash -c ". secrets && envsubst < env/envapi.template > env/.envapi"

-include env/.envaccounts

up:
	docker-compose up -d

down:
	docker-compose down

release:
	docker push dina/keycloak:v0.1
	docker push dina/accounts-api:v0.1
	docker push dina/accounts-ui:v0.1

