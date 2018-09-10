# ExtensionRepository 
[![Build Status](https://travis-ci.org/TeamMistique/ExtensionRepository.svg?branch=master)](https://travis-ci.org/TeamMistique/ExtensionRepository.svg?branch=master)   [![codecov](https://codecov.io/gh/TeamMistique/ExtensionRepository/branch/master/graph/badge.svg)](https://codecov.io/gh/TeamMistique/ExtensionRepository)

Final Project Assignment - Telerik Academy Alpha - Java

A simple one page web application - Visual Studio Code-like extension repository system. It allows extension developers to manage their extensions.The clients of the system must be able to browse the extensions and download them. 

Public homepage includes three lists of extensions - new, popular and featured (admin's selection) and a search functionality that allows guests and users to browse through all published extensions.

Registered users can create their own extensions which should be approved from administrators before being publicly visible to other users and guests.

Except for approving extensions, admins can edit and delete all extensions, disable users, feature extensions, trigger sync of github data for extensions or change the default period over which sync fires automatically.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. Deplyment instructions coming soon!!!

### Prerequisites

* [Java 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [MariaDB](https://mariadb.com)

### Installing

Clone or download the github repository on your computer

```
$ git clone https://github.com/TeamMistique/ExtensionRepository
```

If you wish to get the sample data provided to play around with the application, simply checkout the sample-data branch by navigating to the root folder of the repository and executing the following command in terminal.

```
$ git checkout --track origin/sample-data
```

At the command line, log in to MySQL as the root user:

```
$ mysql -u root -p
```
Type the MySQL root password, and then press Enter.

To create a database user, type the following command. 

```
$ GRANT ALL PRIVILEGES ON *.* TO 'defuser'@'localhost' IDENTIFIED BY 'defuser';
```
Type \q to exit the mysql program. To log in to MySQL as the user you just created, type the following command. 

```
$ mysql -u defuser -p
```
Type the user's password (defuser), and then press Enter. To process the SQL script and create the database, type the following command. 

```
$ mysql -u defuser -p < extensionrepositoryDB.sql
```
If you wish to populate the database with sample data, just execute the other SQL script by typing the following command.

```
$ mysql -u defuser -p < insertIntoDB.sql
```

To build and run the application run the following command if you have only Java 1.8 on your computer. In case you have a more recent version, you should navigate to the java 1.8 jdk by following the tutorial here https://confluence.atlassian.com/doc/setting-the-java_home-variable-in-windows-8895.html and then run the command.

```
$./gradlew bootRun
```

Go to localhost:8080 in your web browser to open the application! 

##Rest API

Find some of the REST methods documented here https://documenter.getpostman.com/view/4891736/RWaGVVhA
More coming soon.
