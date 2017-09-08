# Accounts

This project contains components for account management functionality.

## Overview

Back-end services include KeyCloak and a database and a REST API wrapping the KeyCloak API and exposing additional functionality. There is also a front-end component in EmberJS. 

To run these components as a standalone minimal system, you also need to launch a reverse proxy for web traffic routing and SSL termination.

## Bootstrapping

To build from source and run the system locally you need a *nix host. This can be your laptop, a virtual machine or an existing *nix server. 

It needs to be setup with `docker` and `docker-compose` as well as have `make` and `git` installed and it needs to be configured to resolve names (in the simplest way just by editing `/etc/hosts`). 

More details about setting up your host are available here: https://github.com/DINA-Web/bootstrap.

## Step-by-step instructions

Here is an attempt to provide a short recipe of commands you can use to get the necessary parts in place, running on your host.

		# backend with REST API
		git clone $THIS_REPO_SLUG
		cd accounts-docker
	
		# NB: before building and starting services, make sure to configure your email server settings
		# to do this run "make secrets" and then add extra info to the "secrets" file and then run ...
		make dotfiles

		# build and run
		make

		# acccess the UI
		firefox https://beta-accounts.dina-web.net

NB: A local build will initially pulls many dependencies (~150+M maven libs for the API, ~1.4G npm packages for the UI) and takes approx 20 minutes depending on Internet connection speed. Re-building is faster, approx a couple of minutes at the most.

When building on MacOS, the "envsubst" command used in `make dotfiles` may not be available by default. It can be installed with:

		brew install gettext
		brew link --force gettext 

To configure email server settings, please edit the "secrets" file and fill in the missing values after having run "make secrets", then run "make dotfiles"

Once the services have launched, open up your browser at beta-accounts.dina-web.net and log in with the default Accounts API user credentials from the 'envapi.template' file that you have used, usually user: admin@nrm.se and pass: admin#001.

### Other Settings

We suggest you add the following entries to the `/etc/hosts` file so that your host responds to the above services:

		beta-accounts.dina-web.net

## Issues

Currently these are some known issues that stops the system from being fully functional upon start. 

Please see the Issues list of this repository for details.


