# Accounts

This project contains components for account management functionality.

## Overview

Back-end services include KeyCloak and a database and a REST API wrapping the KeyCloak API and exposing additional functionality. There is also a front-end component in EmberJS. To run these components as a standalone minimal system, you also need to launch a reverse proxy for web traffic routing and SSL termination.

## Step-by-step instructions

Here is an attempt to provide a short recipe of commands you can use to get the necessary parts in place.

		# backend with REST API
		git clone $THIS_REPO_SLUG
		cd accounts-docker
	
		# build and run
    make dotfiles
		make

		# acccess the UI
		firefox https://beta-accounts.dina-web.net

NB: A local build will initially pulls many dependencies (~150+M maven libs for the API, ~1.4G npm packages for the UI) and takes approx 20 minutes depending on Internet connection speed. Re-building is faster, approx a couple of minutes at the most.

### Other Settings

We suggest you add the following entries to the `/etc/hosts` file so that your host responds to the above services:

		beta-accounts.dina-web.net

## Issues

Currently these are some known issues that stops the system from being fully functional upon start. 

Please see the Issues list of this repository for details.


