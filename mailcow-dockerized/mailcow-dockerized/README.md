# mailcow: dockerized - 🐮 + 🐋 = 💕
[![master build status](https://img.shields.io/drone/build/mailcow/mailcow-dockerized/master?label=master%20build&server=https%3A%2F%2Fdrone.mailcow.email)](https://drone.mailcow.email/mailcow/mailcow-dockerized) [![staging build status](https://img.shields.io/drone/build/mailcow/mailcow-dockerized/staging?label=staging%20build&server=https%3A%2F%2Fdrone.mailcow.email)](https://drone.mailcow.email/mailcow/mailcow-dockerized) [![Translation status](https://translate.mailcow.email/widgets/mailcow-dockerized/-/translation/svg-badge.svg)](https://translate.mailcow.email/engage/mailcow-dockerized/)

## Want to support mailcow?

Please [consider a support contract with Servercow](https://www.servercow.de/mailcow?lang=en#support) to support further development. _We_ support _you_ while _you_ support _us_. :)

You can also [get a SAL](https://www.servercow.de/mailcow?lang=en#sal) which is a one-time payment with no liabilities or returning fees.

Or just spread the word: moo.

## Info, documentation and support

Please see [the official documentation](https://mailcow.github.io/mailcow-dockerized-docs/) for installation and support instructions. 🐄

🐛 **If you found a critical security issue, please mail us to [info at servercow.de](mailto:info@servercow.de).**

## Cowmunity

[mailcow community](https://community.mailcow.email)

[Telegram mailcow channel](https://telegram.me/mailcow)

[Telegram mailcow Off-Topic channel](https://t.me/mailcowOfftopic)

Telegram desktop clients are available for [multiple platforms](https://desktop.telegram.org). You can search the groups history for keywords.

## Misc

**Important**: mailcow makes use of various open-source software. Please assure you agree with their license before using mailcow.
Any part of mailcow itself is released under **GNU General Public License, Version 3**.


## Steps to install and use with docker

### Preq

docker-compose version >= 2.0
docker version >= 20.2.0

Because the script has a bunch of GNU utils in it, mac users should install the coreutils of GNU
brew install coreutils findutils gnu-tar gnu-sed gawk gnutls gnu-indent gnu-getopt grep


### The process

1. run ./genereate_config.sh \
The script will ask you to fill in FQDN and Timezone \
FQDN: mail.ubuntu.server
Timezone: default (hit enter)

Done with script.

2. docker-compose-v1 up -d

3. go to localhost:8080


