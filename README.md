# Accounts

This project contains components for account management functionality.

This includes KeyCloak, a REST API and a database. There is also a front-end component in EmberJS.

To run these components as a standalone minimal system, you also need to launch a reverse proxy for web traffic routing and SSL termination.

## Step-by-step instructions

### Services

Here is an attempt to provide a short recipe of commands you can use to get the necessary parts in place.

		# backend with REST API
		git clone $THIS_REPO_SLUG
		cd accounts-docker
		make
		cd ..

    # acccess the UI
    firefox https://beta-accounts.dina-web.net

### Other Settings

We suggest you add the following entries to the `/etc/hosts` file so that your host responds to the above services:

		beta-accounts.dina-web.net

## Issues

Currently these are these known issues:

- The system does not allow import and export of data from CSV yet (so it starts empty)

- Starting the database with utf8 character set parameters such as the ones below will trigger a KeyCloak bug which seems to be described here: http://stackoverflow.com/questions/23781420/can-applied-liquibase-changesets-be-replaced.

	# settings causing KeyCloak error (current workaround: these settings are not used)
	#    command: mysqld --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci

- The API component does not launch cleanly, which seems to be related to this issue: http://stackoverflow.com/questions/37273621/fail-to-start-jax-rs-service-on-wildfly-swarm

