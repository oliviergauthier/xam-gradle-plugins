# xam-gradle-plugin 

Set of gradle plugins to build Xamarin mobile application (iOS/Android) and librairies.
- xam-application-plugin
- xam-library-plugin
- xam-test-plugin 


## xam-library-plugin

This plugin provides following gradle  tasks :
- build : build library with xbuild
- package : create nuget package
- install : install package in local folder
- deploy : deploy package on nuget server like (NugetServer, Sonatype Nexus, ...)

### Minimum configuration requirement

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

    authors "John Doe"
    owners "com.acme"
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

```bash
~/SampleLib(master)$ gradle tasks
Starting a Gradle Daemon (subsequent builds will be faster)
:tasks

------------------------------------------------------------
All tasks runnable from root project
------------------------------------------------------------

Build tasks
-----------
build - build library
clean - clean library
cleanAll - clean all dist and library
cleanDist - clean dist directory
nugetRestore - restore nuget packages

Package tasks
-------------
generateNuspec - Generate nuspec files
package -  Packages libraries with nuget

Publish tasks
-------------
deploy - Deploy libraries on remote server
install - Install libraries locally

To see all tasks and more detail, run gradle tasks --all

To see more detail about a task, run gradle help --task <task>

BUILD SUCCESSFUL

Total time: 5.213 secs

```

### Complete DSL