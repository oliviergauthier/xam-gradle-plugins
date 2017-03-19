# Xamarin gradle plugins

Set of gradle plugins to build Xamarin mobile application (iOS/Android) and librairies.

* **xamarin-application-plugin :** Build ios/android Application
* **xamarin-library-plugin :** Build/Package/Deploy Nuget Library
* **xamarin-test-plugin :** Build and run nunit test

## Documentations :
- [Wiki](./wiki)
- SampleApp
- SampleLib

## Install
Install in local repository 
```bash
gradlew install
```

## Deploy
Deploy on personal repository like nexus (Sonatype)
```bash
gradlew publish -Ppassword=<PASSWORD> -Puser=<USERNAME> -Purl=<REPOSITORY_URL>
```