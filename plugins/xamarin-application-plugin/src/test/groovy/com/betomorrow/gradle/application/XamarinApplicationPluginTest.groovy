package com.betomorrow.gradle.application

import com.betomorrow.gradle.application.tasks.BuildAndroidAppTask
import com.betomorrow.gradle.application.tasks.BuildIOSAppTask
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Before
import org.junit.Test

import java.nio.file.Paths

class XamarinApplicationPluginTest {

    Project project

    @Before
     void setUp() {
        project = ProjectBuilder.builder().withProjectDir( new File('src/test/resources')).build()

        project.apply plugin: 'xamarin-application-plugin'

        project.solution = 'CrossApp/CrossApp.sln' // first solution file in current folder
    }


    @Test
     void testApplyCreatesBuildAndroidTasksWithResolvedValues() {
        project.evaluate()

        BuildAndroidAppTask buildAndroidTask = project.tasks.buildAndroid

        assert buildAndroidTask.configuration == 'Release'
        assert buildAndroidTask.appVersion == "1.0"
        assert buildAndroidTask.versionCode == "2.6"
        assert buildAndroidTask.packageName ==  "com.acme.crossapp"
        assert buildAndroidTask.output == "dist/CrossApp.Droid-1.0.apk"
        assert Paths.get(buildAndroidTask.projectFile) == project.file("CrossApp/Droid/CrossApp.Droid.csproj").toPath()
        assert Paths.get(buildAndroidTask.manifest) == project.file("CrossApp/Droid/Properties/AndroidManifest.xml").toPath()

    }

    @Test
     void testApplyCreatesBuildIOSTasksWithResolvedValues() {
        project.evaluate()

        BuildIOSAppTask buildIOSTask = project.tasks.buildIOS

        assert buildIOSTask.configuration == 'Release'
        assert buildIOSTask.bundleVersion == "1.0"
        assert buildIOSTask.bundleShortVersion == "2.6"
        assert buildIOSTask.bundleIdentifier == "com.sample.crossapp"
        assert buildIOSTask.output == "dist/CrossApp.iOS-1.0.ipa"
        assert Paths.get(buildIOSTask.projectFile) == project.file("CrossApp/iOS/CrossApp.iOS.csproj").toPath()
        assert Paths.get(buildIOSTask.infoPlist) == project.file("CrossApp/iOS/Info.plist").toPath()
        assert buildIOSTask.platform == "iPhone"

    }

    @Test
     void testApplyCreatesBuildAndroidTaskWithOverridedValues() {
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

        BuildAndroidAppTask buildAndroidTask = project.tasks.buildAndroid
        assert buildAndroidTask.configuration == 'Release'
        assert buildAndroidTask.appVersion == "2.6"
        assert buildAndroidTask.versionCode == "1.0"
        assert buildAndroidTask.packageName ==  "com.acme.crossapp"
        assert buildAndroidTask.output == "dist/my-CrossApp.Droid-2.6.apk"
        assert buildAndroidTask.projectFile == "path/to/myapp"
        assert buildAndroidTask.manifest == "path/to/manifest"
    }

    @Test
     void testApplyCreatesBuildIOSTaskWithOverridedValues() {
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

        BuildIOSAppTask buildIOSTask = project.tasks.buildIOS

        assert buildIOSTask.configuration == 'Release'
        assert buildIOSTask.bundleVersion == "2.6"
        assert buildIOSTask.bundleShortVersion == "1.0"
        assert buildIOSTask.bundleIdentifier == "com.acme.crossapp"
        assert buildIOSTask.output == "dist/my-CrossApp.iOS-2.6.ipa"
        assert buildIOSTask.projectFile == "path/to/myapp"
        assert buildIOSTask.infoPlist == "path/to/Info.plist"
        assert buildIOSTask.platform == "iPhoneSimulator"
    }

    @Test
     void testDryRun() {
        project.application {

            dryRun = true

            appName 'CrossApp' // auto resolved (common part of all projects names in solution)
            appVersion '2.6' // if empty use the one defined in csproj
            storeVersion '1.0' // if empty use the one defined in csproj

            packageName "com.acme.crossapp" // if empty use the one defined in csproj

            android {
                manifest "path/to/manifest" // auto resolved
                output "dist/my-${appName}-${appVersion}.apk"  // default value
                //projectFile "path/to/myapp" // auto resolved
            }

            ios {
                output "dist/${appName}-${appVersion}.ipa"  // default value
            }
        }

        project.evaluate()

        BuildAndroidAppTask buildAndroidTask = project.tasks.buildAndroid
        buildAndroidTask.actions.each { action -> action.execute(buildAndroidTask) }
    }
}
