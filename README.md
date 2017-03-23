Xamarin gradle plugins
==========

# Build status

[![Build Status](https://travis-ci.org/oliviergauthier/xam-gradle-plugins.svg?branch=master)](https://travis-ci.org/oliviergauthier/xam-gradle-plugins)

# Description

Set of [Gradle](https://gradle.org/) plugins to build Xamarin mobile application (iOS/Android) and librairies from cli.

* **[xamarin-application-plugin](#xamarin-application-plugin) :** Build ios/android Application
* **[xamarin-library-plugin](#xamarin-library-plugin) :** Build/Package/Deploy Nuget Library
* **[xamarin-test-plugin](#xamarin-nunit-plugin) :** Build and run nunit test

# Documentation :

See following projects for working samples
- [SampleApp](https://github.com/oliviergauthier/xam-gradle-plugins-sample-app)
- [SampleLib](https://github.com/oliviergauthier/xam-gradle-plugins-sample-lib)

## Xamarin Application Plugin 

This plugin provides the ability to build iOS and Android Xamarin Applications with gradle.

**Android Application:**
* Update versionCode, storeVersion and packageName in AndroidManifest
* Build apk

**Ios Application:**
* Update version, shortVersion and bundleIdentifier in Info.plist
* Build ipa

**Tasks :**
- buildIOS : build iOS application
- buildAndroid : build Android Application

**Notes :**
* By default, use first solution file found in project folder
* By default, use Release configuration

###Minimum requirement

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

### Full DSL

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

application {

    // global settings
    solution = "SampleApp.sln"          // default : First solution file found in project folder
    configuration = "Release"           // default : Release
    appName = "SampleApp"               // default : project.name
    appVersion = "42"                   // versionName (Android) / shortVersion (iOS)
    storeVersion = "1.0"                // versionCode (Android) / version (iOS)
    packageName = "com.acme.SampleApp"  // packageName (Android) / bundleIdentifier (iOS)

    android {
        appName = "SampleApp.Droid"                         // default : Droid or first android project in solution 
        output = "dist/SampleApp.apk"                       // default : dist/<appName>-<appVersion>.apk
        manifest = "Droid/Properties/AndroidManifest.xml"   // default : <Android Project Dir>/Properties/AndroidManifest.xml
        projectFile = "Droid/SampleApp.Droid.csproj"        // default : csproj of project name <appName>
        appVersion = 42                                     // (read-only) default : inherited from global settings
        storeVersion = "1.0"                                // (read-only) default : inherited from global settings
        packageName = "com.acme.SampleApp"                  // (read-only) default : inherited from global settings
    } 

    ios {
        appName = "SampleApp.iOS"                           // default : Droid or first android project in solution 
        output = "dist/SampleApp.ipa"                       // default : dist/<appName>-<appVersion>.apk
        infoPlist = "iOS/Properties/Info.plist"             // default : <Android Project Dir>/Properties/AndroidManifest.xml
        projectFile = "iOS/SampleApp.iOS.csproj"                // default : csproj of project name <appName>
        bundleShortVersion = "1.0"                          // (read-only) default : inherited from global settings
        bundleVersion = "1.0"                               // (read-only) default : inherited from global settings
        bundleIdentifier = "com.acme.SampleApp"             // (read-only) default : inherited from global settings
        platform = "iPhone"                                 // default : iphone
    }
}
```

## Xamarin Library Plugin 

This plugin provides the ability to build PCL Libraries and Nuget Packages

**Tasks :**
- build : build library with xbuild
- package : create nuget package
- install : install package locally
- deploy : deploy package on nuget server like (NugetServer, Sonatype Nexus, ...)

**Notes :**
* By default, use first solution file found in project folder
* in packages.*.assemblies.target.includes, you can specify assembly path or csproj name references in solution. Plugin will resolve assemblies of project

### Minimum requirement

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
            assemblies {
                target {
                    dest "lib/portable-net45+wp8+wpa81+win8+MonoAndroid10+MonoTouch10+Xamarin.iOS10"
                    includes "SampleLib"
                }
            }
        }
    }
}
```

### Full DSL
```
buildscript {
    repositories {
        mavenLocal()
    }
    dependencies {
        classpath 'com.betomorrow.gradle:xamarin-library-plugin:1.0-SNAPSHOT'
    }
}

apply plugin: 'xamarin-library-plugin'

name = "Xam.ACME.CrossLib"
version = "1.0.0-SNAPSHOT"

library {
    solution      // default : first sln file found in current folder
    configuration // default : Release
}

nuspec {

     // Global nuspec properties

    version // default : version number from project.version
    suffix // default : suffix version from project.version
    authors
    owners
    licenseUrl
    projectUrl
    iconUrl
    requireLicenseAcceptance
    description
    releaseNotes
    copyright
    tags

    packages {
        SampleLib {

            // Following properties are inherited from global nuspec properties

            version
            suffix
            authors
            owners
            licenseUrl
            projectUrl
            iconUrl
            requireLicenseAcceptance
            description
            releaseNotes
            copyright
            tags

            dependencies {
                dependency "Xamarin.Forms:[1.4.3,)" // <Name>:<Version Restriction>
                [...]
            }

            assemblies {
                target {
                    dest "lib/portable-net45+wp8+wpa81+win8+MonoAndroid10+MonoTouch10+Xamarin.iOS10" // directory in nuget package
                    includes "CrossLib.Abstractions", "CrossLib" // Project assemblies to include
                }
                [...]
            }
        }
    }
}

deploy {
    local {
        path "fake/directory"   // install directory, default : ~/.nuget/packages
        format "nuget2"         // nuget format (nuget2|nuget3), default : nuget3
    }
    remote {
        url = "http://fake.url" // remote repository url
        apiKey = "12345789"     // nuget apiKey
    }
}

```

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

