# Accounts

This project contains components for user (account) management functionality.

## Overview

Back-end services include:

- Keycloak and a REST JSON-API wrapping the Keycloak API and exposing additional functionality
- MySQL database for Keycloak
- Frontend UI in Ember.js

## Bootstrapping

To build from source and run the system locally you need a *nix host - your own computer, a virtual machine or a server. 

Requirements:
- `docker`
- `docker-compose`
- `make`
- `git`
- [DINA Reverse proxy](https://github.com/DINA-Web/proxy-docker) for web traffic routing and SSL termination.
- [DINA Mailserver](https://github.com/DINA-Web/mail-docker)

See bootstrap repository for [details about setting up your host](https://github.com/DINA-Web/bootstrap).

## Step-by-step instructions

A recipe of commands to get the necessary parts in place, running on your host:

1) Get latest version of the module

		git clone $THIS_REPO_SLUG
		cd accounts-docker

2) Create secrets

		make secrets

3) Configure email settings

To configure email server settings, edit the "secrets" file and fill in the missing values.

4) Make dotfiles, which will contain environment variables.

		make dotfiles

5) Build and run Docker containers

		make

NB: A local build will initially pulls many dependencies (~150+M maven libs for the API, ~1.4G npm packages for the UI) and takes c. 20 minutes depending on Internet connection speed. Re-building is faster, a couple of minutes at the most.

6) Acccess the UI

Add the following entries to the `/etc/hosts` file so that your host responds to the above services:

		beta-accounts.dina-web.net

Then open up your browser at https://beta-accounts.dina-web.net

		firefox https://beta-accounts.dina-web.net

Log in with the default Accounts API user credentials from the 'envapi.template' file that you have used, usually user: admin@nrm.se and pass: admin#001.

### Building on Mac

When building on MacOS, the "envsubst" command used in `make dotfiles` may not be available by default. It can be installed with:

		brew install gettext
		brew link --force gettext 

## Issues

Currently these are some known issues that stops the system from being fully functional upon start. 

Please see the Issues list of this repository for details.


