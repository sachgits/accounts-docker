#!make
 
PWD=$(shell pwd)

all: init secrets dotfiles
# build (check docker-images)... up .... docker-compose up -d api

.PHONY: all

init:
	test -f env/.envmailserver || cp env/envmailserver.template env/.envmailserver
	@echo "enter the credentials to the .envmailserver-file before continuing , then run secrets"
	vi env/.envmailserver

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
		danlynn/ember-cli:2.11.1 \
      sh -c "npm install && bower --allow-root install && ember build" 
	make -C accounts-ui
 

dox:
	@echo "Rendering API Blueprint into HTLM documentation using aglio"
	docker pull humangeo/aglio
	docker run -ti --rm -v $(PWD)/:/docs humangeo/aglio \
		aglio -i apiary.apib -o accounts-api-reference.html
	sudo chown $(USR):$(USR) accounts-api-reference.html
	firefox accounts-api-reference.html
 
secrets:
	#echo "# Make this unique, and don't share it with anybody.\n# This value was autogenerated." > $@
	#rm -f secrets env/{.envmysql}
	printf "export SECRET_MYSQL_ROOT_PASSWORD=%b\n" \
		$$(cat /dev/urandom | LC_CTYPE=C tr -dc 'a-zA-Z0-9' | head -c 50) >> $@
	printf "export SECRET_MYSQL_PASSWORD=%b\n" \
		$$(cat /dev/urandom | LC_CTYPE=C tr -dc 'a-zA-Z0-9' | head -c 50) >> $@
	printf "`cat env/.envmailserver`">> $@
	printf "\n" >> $@
	printf "export ACCOUNTS_ROOT=admin\n" >> $@
	printf "export ACCOUNTS_PASS=dina\n" >> $@
	

dotfiles:
	echo "remember to update the secrets file with mailserver-credentials"
	bash -c ". secrets && envsubst < env/envmysql.template > env/.envmysql"
	bash -c ". secrets && envsubst < env/envaccounts.template > env/.envaccounts"
	bash -c ". secrets && envsubst < env/envapi.template > env/.envapi"

-include env/.envaccounts

up:
	#docker-compose up -d
	docker-compose up -d db sso ui ws proxy

rm: 
	rm -rf ${PWD}/accounts-ui/dist && sudo rm -rf ${PWD}/accounts-api/target

down:
	docker-compose down

clean: down
	# remove builds
	rm -rf ${PWD}/accounts-ui/dist && sudo rm -rf ${PWD}/accounts-api/target
	# remove .env-files
	rm -rf $(PWD)/env/.envaccounts && rm -rf $(PWD)/env/.envapi && rm -rf $(PWD)/env/.envmysql && rm -rf $(PWD)/env/.envmailserver
	# remove all images
	docker rmi -f dina/keycloak:v0.1 dina/accounts-ui:v0.1 dina/accounts-api:v0.1
	# remove volume
	docker volume rm accountsdocker_db_accounts

release:
	docker push dina/keycloak:v0.1
	docker push dina/accounts-api:v0.1
	docker push dina/accounts-ui:v0.1

