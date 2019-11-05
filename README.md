[![Build status](https://travis-ci.org/thehyve/transmart-lib.svg?branch=master)](https://travis-ci.org/thehyve/transmart-lib/branches)
[![codecov](https://codecov.io/gh/thehyve/transmart-lib/branch/master/graph/badge.svg)](https://codecov.io/gh/thehyve/transmart-lib)
[![codebeat](https://codebeat.co/badges/a2b8dbaa-c40c-4045-adde-ebc7de4d8916)](https://codebeat.co/a/gijs-kant/projects/github-com-thehyve-transmart-lib-master)


# TranSMART API library


## Overview

The packages in the TranSMART API library depend on Java 11.
To add the Nexus repository where the packages are published, add the following
to your Maven configuration: 
```xml
<repositories>
    <repository>
        <id>nl.thehyve.nexus.releases</id>
        <name>The Hyve - Nexus releases repository</name>
        <url>https://repo.thehyve.nl/content/repositories/releases/</url>
    </repository>
</repositories>
```

This repository contains the following packages:

### transmart-common

Data classes, resource specifications and Feign clients for the TranSMART
REST API.
```xml
<dependency>
    <groupId>org.transmartproject</groupId>
    <artifactId>transmart-common</artifactId>
    <version>0.0.4</version>
</dependency>
```

### transmart-proxy

Proxy server controllers and client services.
```xml
<dependency>
    <groupId>org.transmartproject</groupId>
    <artifactId>transmart-proxy</artifactId>
    <version>0.0.4</version>
</dependency>
```

### transmart-proxy-server

Spring Boot application that serves as a TranSMART REST API proxy.
See below for instructions on running the proxy server.


#### Configure Keycloak and TranSMART connection

The transmart-proxy-server uses Keycloak as an identity provider.
The following settings need to be configured before running the application.

| Property                                | Description
|:--------------------------------------- |:--------------------------------
| `keycloak.auth-server-url`              | Keycloak url, e.g, `https://keycloak.example.com/auth`
| `keycloak.realm`                        | Keycloak realm.
| `keycloak.resource`                     | Keycloak client id.
| `transmart-client.transmart-server-url` | The TranSMART server url, e.g., `https://transmart-dev.thehyve.net`

See [application-dev.yml](transmart-proxy-server/src/main/resources/config/application-dev.yml)
for example configuration.

#### Run

Make sure you have Maven installed.
```bash
# create a jar package
mvn clean package
```
There should now be a `.jar`-file in `transmart-proxy-server/target/transmart-proxy-server-<version>.jar`.
```bash
# run the packaged application
java -jar transmart-proxy-server/target/transmart-proxy-server-<version>.jar
```

There should now be an application running at [http://localhost:9050/](http://localhost:9050/).

#### Use

There is a [Swagger documentation](https://swagger.io/solutions/api-documentation/) describing how to use the transmart-proxy API.
It is exposed at the application URL, by default: [http://localhost:9050/](http://localhost:9050/).


## Development

### Run in development mode

```bash
cd transmart-proxy-server
# run the application in development mode
mvn spring-boot:run -Dspring.profiles.active=dev
```

### Install
```bash
# install the package locally
mvn clean install
```

### Tests

Run all tests:
```bash
mvn test
```


## Acknowledgement

This project was funded by the German Ministry of Education and Research (BMBF) as part of the project
DIFUTURE - Data Integration for Future Medicine within the German Medical Informatics Initiative (grant no. 01ZZ1804D).


## License

Copyright &copy; 2019  The Hyve B.V.

transmart-lib is free software: you can redistribute it and/or
modify it under the terms of the GNU General Public License as
published by the Free Software Foundation, either version 3 of
the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the [GNU General Public License](LICENSE) along with this program.
If not, see https://www.gnu.org/licenses/.
