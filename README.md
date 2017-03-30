Xamarin gradle plugins
==========

# Build status

[![Build Status](https://travis-ci.org/oliviergauthier/xam-gradle-plugins.svg?branch=master)](https://travis-ci.org/oliviergauthier/xam-gradle-plugins)
[ ![Download](https://api.bintray.com/packages/oliviergauthier/gradle/xamarin-build-tools/images/download.svg?version=0.2.0) ](https://bintray.com/oliviergauthier/gradle/xamarin-build-tools/0.2.0/link)

# Description

Set of [Gradle](https://gradle.org/) plugins to build Xamarin mobile application (iOS/Android) and librairies from cli.

* **[xamarin-application-plugin](#xamarin-application-plugin) :** Build ios/android Application
* **[xamarin-library-plugin](#xamarin-library-plugin) :** Build/Package/Deploy Nuget Library
* **[xamarin-test-plugin](#xamarin-nunit-plugin) :** Build and run nunit test

# Quick Start

## Xamarin Application Plugin 

This plugin provides the ability to build iOS and Android Xamarin Applications with gradle.

###### Tasks
- buildIOS : build iOS application
- buildAndroid : build Android Application

###### Gradle >= 2.1
```groovy
plugins {
  id "com.betomorrow.xamarin.application" version "0.2.0"
}
```

###### Gradle < 2.1
```
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "gradle.plugin.com.betomorrow.gradle:xamarin-application-plugin:0.2.0"
  }
}

apply plugin: "com.betomorrow.xamarin.application"
```

[More info](/docs/Application.md)

## Xamarin Library Plugin 

This plugin provides the ability to build PCL Libraries and Nuget Packages

###### Tasks
- build : build library with xbuild
- package : create nuget package
- install : install package locally
- deploy : deploy package on nuget server like (NugetServer, Sonatype Nexus, ...)

###### Gradle >= 2.1
```groovy
plugins {
  id "com.betomorrow.xamarin.library" version "0.2.0"
}
```

###### Gradle < 2.1
```
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "gradle.plugin.com.betomorrow.gradle:xamarin-library-plugin:0.2.0"
  }
}

apply plugin: "com.betomorrow.xamarin.library"
```

###### Configuration
```groovy

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

[More info](/docs/Library.md)

## Xamarin NUnit Plugin 

This plugin provides the ability to build and run NUnit test

###### Tasks
- compileTest : build test with xbuild
- test : run nunit-console

###### Gradle >= 2.1
```groovy
plugins {
  id "com.betomorrow.xamarin.nunit" version "0.2.0"
}
```

###### Gradle < 2.1
```
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "gradle.plugin.com.betomorrow.gradle:xamarin-nunit-plugin:0.2.0"
  }
}

apply plugin: "com.betomorrow.xamarin.nunit"
```

###### Configuration
```
nunit {
    assemblies = "path/to/assemblies.Test.dll"  // (optional) array or string, full path of test assemblies
    projects = "CrossLib.Test"                  // (optional) By default, use "${project.name}.Test"
    format = "nunit2"                           // Result test format
}

```

See following projects for more working samples
- [SampleApp](https://github.com/oliviergauthier/xam-gradle-plugins-sample-app)
- [SampleLib](https://github.com/oliviergauthier/xam-gradle-plugins-sample-lib)


