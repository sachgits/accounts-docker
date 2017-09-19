# Accounts

[![Build Status](https://travis-ci.org/DINA-Web/accounts-docker.svg?branch=development)](https://travis-ci.org/DINA-Web/accounts-docker)
[![codecov](https://codecov.io/gh/DINA-Web/accounts-docker/branch/development/graph/badge.svg)](https://codecov.io/gh/DINA-Web/accounts-docker)
 
## Overview

This project contains components for account management functionality:

- Keycloak and a REST JSON-API wrapping the Keycloak API and exposing additional functionality
- MySQL database for Keycloak
- Frontend UI in Ember.js

## Building locally

To build from source and run the system locally you need a \*nix host - your own computer, a virtual machine or a server with the following software:

- `docker`
- `docker-compose`
- `make`
- `git`

See the bootstrap repository for [details about setting up your host](https://github.com/DINA-Web/bootstrap).

The official guide for installing Docker on for example Ubuntu also suggests some post-install steps for example allowing Docker to be managed by a non-root user, see for example: <https://docs.docker.com/engine/installation/linux/linux-postinstall/>

## Email

For the system to be able to send email, an email server connection needs to be configured. 

You can use an existing email service (3rd party or the email server at your home institution), or you can run this dockerized email server to provide email services:
 
- [DINA-Web dockerized email server](https://github.com/DINA-Web/mail-docker)

## Step-by-step instructions

A recipe of commands to get the necessary parts in place, running on your host.


		# get the code for this project
		git clone $THIS_REPO_SLUG
		cd accounts-docker

		# to use the development branch
		git fetch
		git checkout development

		# generate random credentials
		make secrets

		# configure email settings, fill in email connection settings
		nano secrets

		# generate 'dotfiles' using the secret credentials above
		make dotfiles

		# set up reverse proxy with self-signed certs
		# follow instructions at https://github.com/dina-web/proxy-docker/tree/self-signed-certs


		# build locally and launch services
		make


**Note**: A local build will initially pull many dependencies (~150+M maven libs for the API, ~1.4G npm packages for the UI) and takes c. 20 minutes depending on Internet connection speed. Re-building is faster, a couple of minutes at the most.

You can also use `make up` to start the system from pre-existing images. If these are not present locally, Docker will pull these from DINA's account on Docker Hub, provided that relevant versions have been released there.

**Note**: Add the following entries to the `/etc/hosts` file so that your host responds to the above services:

		127.0.0.1	beta-accounts.dina-web.net beta-api.dina-web.net beta-sso.dina-web.net

# Logging in

Open up your browser at https://beta-accounts.dina-web.net, remember to install any self-signed certs first, see https://github.com/dina-web/proxy-docker/tree/self-signed-certs

		firefox https://beta-accounts.dina-web.net


Log in with the default Accounts API user credentials from the 'envapi.template' file that you have used, usually user: admin@nrm.se and pass: admin#001.

# Using the latest changes

To use a branch with the latest changes, use the development branch:

		git fetch
		git checkout development
		git pull

Regenerate dotfiles from your local secrets and rebuild & start the services.

		make dotfiles
		make
		
Some things might require manual upgrades (e.g. regenerating secrets file, if new random secrets are needed somewhere) - check these from release notes.

# Building on Mac

When building on MacOS, the "envsubst" command used in `make dotfiles` may not be available by default. It can be installed with:

		brew install gettext
		brew link --force gettext 

# Issues

Currently these are some known issues that stops the system from being fully functional upon start. 

Please see the Issues list of this repository for details.
