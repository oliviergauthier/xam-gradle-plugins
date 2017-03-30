# Xamarin Application Plugin 

This plugin provides the ability to build iOS and Android Xamarin Applications with Gradle. 
It can be used to integrate build in Continus Integration System like Jenkins

# What you can do easily ?

**Android Application**
* Customize versionCode, storeVersion and packageName and apk name
* Customize AndroidManifest.xml to add API_KEY for example
* Build application

**Ios Application**
* Customize Update version, shortVersion and bundleIdentifier in Info.plist
* Update Info.plist
* Build ipa

**Tasks :**
- buildIOS : build iOS application
- buildAndroid : build Android Application

# Configuration

**Notes :**
* By default, use first solution file found in project folder
* By default, use Release configuration

```groovy
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
