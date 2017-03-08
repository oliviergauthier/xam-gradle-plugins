package com.betomorrow.gradle.application

import com.betomorrow.gradle.application.tasks.BuildAndroidAppTask
import com.betomorrow.gradle.application.tasks.BuildIOSAppTask
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Before
import org.junit.Test
import spock.lang.Specification

import java.nio.file.Paths

class XamarinApplicationPluginTest extends Specification {

    Project project

    def "setup"() {
        project = ProjectBuilder.builder().withProjectDir(new File('src/test/resources/CrossApp/')).build()
        project.apply plugin: 'xamarin-application-plugin'
    }

    def "test apply creates buildAndroid tasks with resolved values"() {
        when:
        project.evaluate()
        BuildAndroidAppTask buildAndroidTask = project.tasks.buildAndroid

        then:
        buildAndroidTask.configuration == 'Release'
        buildAndroidTask.appVersion == "1.0"
        buildAndroidTask.versionCode == "2.6"
        buildAndroidTask.packageName == "com.acme.crossapp"
        buildAndroidTask.output == "dist/CrossApp.Droid-1.0.apk"
        Paths.get(buildAndroidTask.projectFile) == project.file("Droid/CrossApp.Droid.csproj").toPath()
        Paths.get(buildAndroidTask.manifest) == project.file("Droid/Properties/AndroidManifest.xml").toPath()

    }

    def "test apply creates buildIOS task with resolved values"() {
        when:
        project.evaluate()
        BuildIOSAppTask buildIOSTask = project.tasks.buildIOS

        then:
        buildIOSTask.configuration == 'Release'
        buildIOSTask.bundleVersion == "1.0"
        buildIOSTask.bundleShortVersion == "2.6"
        buildIOSTask.bundleIdentifier == "com.sample.crossapp"
        buildIOSTask.output == "dist/CrossApp.iOS-1.0.ipa"
        Paths.get(buildIOSTask.projectFile) == project.file("iOS/CrossApp.iOS.csproj").toPath()
        Paths.get(buildIOSTask.infoPlist) == project.file("iOS/Info.plist").toPath()
        buildIOSTask.platform == "iPhone"
    }


    def "test apply creates buildAndroid task with overrided values"() {
        when:
        project.application {

            appName 'CrossApp' // auto resolved (common part of all projects names in solution)
            appVersion '2.6' // if empty use the one defined in manifest
            storeVersion '1.0' // if empty use the one defined in manifest

            packageName "com.acme.crossapp" // if empty use the one defined in manifet

            android {
                manifest "path/to/manifest" // auto resolved
                output "dist/my-${appName}-${appVersion}.apk"  // default value
                projectFile "path/to/myapp" // auto resolved
            }
        }

        project.evaluate()

        then:
        BuildAndroidAppTask buildAndroidTask = project.tasks.buildAndroid
        buildAndroidTask.configuration == 'Release'
        buildAndroidTask.appVersion == "2.6"
        buildAndroidTask.versionCode == "1.0"
        buildAndroidTask.packageName == "com.acme.crossapp"
        buildAndroidTask.output == "dist/my-CrossApp.Droid-2.6.apk"
        buildAndroidTask.projectFile == "path/to/myapp"
        buildAndroidTask.manifest == "path/to/manifest"
    }


    def "test apply creates buildIOS task with overrided values"() {
        when:
        project.application {

            appName 'CrossApp' // auto resolved (common part of all projects names in solution)
            appVersion '2.6' // if empty use the one defined in manifest
            storeVersion '1.0' // if empty use the one defined in manifest

            packageName "com.acme.crossapp" // if empty use the one defined in manifet

            ios {
                infoPlist "path/to/Info.plist"
                output "dist/my-${appName}-${appVersion}.ipa"  // default value
                projectFile "path/to/myapp"
                platform "iPhoneSimulator"
            }
        }

        project.evaluate()

        then:
        BuildIOSAppTask buildIOSTask = project.tasks.buildIOS

        buildIOSTask.configuration == 'Release'
        buildIOSTask.bundleVersion == "2.6"
        buildIOSTask.bundleShortVersion == "1.0"
        buildIOSTask.bundleIdentifier == "com.acme.crossapp"
        buildIOSTask.output == "dist/my-CrossApp.iOS-2.6.ipa"
        buildIOSTask.projectFile == "path/to/myapp"
        buildIOSTask.infoPlist == "path/to/Info.plist"
        buildIOSTask.platform == "iPhoneSimulator"
    }

}
