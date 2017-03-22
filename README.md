Xamarin gradle plugins
==========

## Build status

[![Build Status](https://travis-ci.org/oliviergauthier/xam-gradle-plugins.svg?branch=master)](https://travis-ci.org/oliviergauthier/xam-gradle-plugins)

## Description

Set of [Gradle](https://gradle.org/) plugins to build Xamarin mobile application (iOS/Android) and librairies from cli.

* **[xamarin-application-plugin](https://github.com/oliviergauthier/xam-gradle-plugins/wiki/Application) :** Build ios/android Application
* **[xamarin-library-plugin](https://github.com/oliviergauthier/xam-gradle-plugins/wiki/Library) :** Build/Package/Deploy Nuget Library
* **[xamarin-test-plugin](https://github.com/oliviergauthier/xam-gradle-plugins/wiki/NUnit) :** Build and run nunit test

## Documentations :
- [Wiki](https://github.com/oliviergauthier/xam-gradle-plugins/wiki)
- [SampleApp](https://github.com/oliviergauthier/xam-gradle-plugins-sample-app)
- [SampleLib](https://github.com/oliviergauthier/xam-gradle-plugins-sample-lib)

## Install
Install in local repository 
```bash
./gradlew install
```

## Deploy
Update `gradle.properties` file with your own url, user and password 
```bash
./gradlew publish
```

