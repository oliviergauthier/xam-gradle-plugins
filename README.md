Xamarin gradle plugins
==========

# Build status

[![Build Status](https://travis-ci.org/oliviergauthier/xam-gradle-plugins.svg?branch=master)](https://travis-ci.org/oliviergauthier/xam-gradle-plugins)

# Description

Set of [Gradle](https://gradle.org/) plugins to build Xamarin mobile application (iOS/Android) and librairies from cli.

* **[xamarin-application-plugin](#xamarin-application-plugin) :** Build ios/android Application
* **[xamarin-library-plugin](#xamarin-library-plugin) :** Build/Package/Deploy Nuget Library
* **[xamarin-test-plugin](#xamarin-nunit-plugin) :** Build and run nunit test

# Install
Install in your local repository with `./gradlew install` or update `gradle.properties` file with your own data and deploy with `./gradlew publish` 


# Quick Start

## Xamarin Application Plugin 

This plugin provides the ability to build iOS and Android Xamarin Applications with gradle.


**Tasks :**
- buildIOS : build iOS application
- buildAndroid : build Android Application

```groovy
buildscript {
   repositories {
       mavenLocal()
   }
   dependencies {
        classpath 'com.betomorrow.gradle:xamarin-application-plugin:1.0-SNAPSHOT'
   }
}

apply plugin: 'xamarin-application-plugin'
```

[More info](/docs/Application)

## Xamarin Library Plugin 

This plugin provides the ability to build PCL Libraries and Nuget Packages

**Tasks :**
- build : build library with xbuild
- package : create nuget package
- install : install package locally
- deploy : deploy package on nuget server like (NugetServer, Sonatype Nexus, ...)

```groovy
buildscript {
   repositories {
       mavenLocal()
   }
   dependencies {
        classpath 'com.betomorrow.gradle:xamarin-library-plugin:1.0-SNAPSHOT'
   }
}

apply plugin: 'xamarin-library-plugin'

version = "1.0.0-SNAPSHOT"

publish {
    remote {
        url = "http://my/custom/repository"
        apiKey = "myNugetApiKey"
    }
}

nuspec {

    description "Sample for nuspec package plugin"

    packages {
        "SampleLib" {
            packageId = "Xam.SampleLib"
            
            dependencies {
                dependency "Xamarin.Forms:[1.4.3,)" // <Name>:<Version Restriction>
                // [...]
            }
                        
            assemblies {
                target {
                    dest "lib/portable-net45+wp8+wpa81+win8+MonoAndroid10+MonoTouch10+Xamarin.iOS10"
                    includes "SampleLib"
                }
                // [...]
            }
        }
    }
}

deploy {
    remote {
        url = "http://fake.url" // remote repository url
        apiKey = "12345789"     // nuget apiKey
    }
}

```

[More info](/docs/Library)

## Xamarin NUnit Plugin 

This plugin provides the ability to build and run NUnit test

**Tasks :**
- compileTest : build test with xbuild
- test : run nunit-console

```
buildscript {
    repositories {
        mavenLocal()
    }
    dependencies {
        classpath 'com.betomorrow.gradle:xamarin-nunit-plugin:1.0-SNAPSHOT'
    }
}

apply plugin: 'xamarin-nunit-plugin'

nunit {
    assemblies = "path/to/assemblies.Test.dll"  // (optional) array or string, full path of test assemblies
    projects = "CrossLib.Test"                  // (optional) By default, use "${project.name}.Test"
    format = "nunit2"                           // Result test format
}

```

See following projects for more working samples
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

