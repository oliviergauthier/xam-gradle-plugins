package com.betomorrow.gradle.application

import com.betomorrow.gradle.application.tasks.BuildAndroidAppTask
import com.betomorrow.gradle.application.tasks.BuildIOSAppTask
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

import java.nio.file.Paths

class XamarinApplicationPluginTest {


    @Test
    public void testApplyCreatesBuildAndroidTasksWithResolvedValues() {
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'xamarin-application-plugin'

        project.application {
            solution 'src/test/resources/CrossApp/CrossApp.sln' // first solution file in current folder
        }

        project.evaluate();

        BuildAndroidAppTask buildAndroidTask = project.tasks.buildAndroid;

        assert buildAndroidTask.configuration == 'Release'
        assert buildAndroidTask.appVersion == "1.0"
        assert buildAndroidTask.versionCode == "2.6"
        assert buildAndroidTask.packageName ==  "com.acme.crossapp"
        assert buildAndroidTask.output == "dist/CrossApp.Droid-1.0.apk"
        assert buildAndroidTask.projectFile == "src/test/resources/CrossApp/Droid/CrossApp.Droid.csproj"
        assert buildAndroidTask.manifest == Paths.get("src/test/resources/CrossApp/Droid/Properties/AndroidManifest.xml").toString()

    }

    @Test
    public void testApplyCreatesBuildIOSTasksWithResolvedValues() {
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'xamarin-application-plugin'

        project.application {
            solution 'src/test/resources/CrossApp/CrossApp.sln' // first solution file in current folder
        }

        project.evaluate();

        BuildIOSAppTask buildIOSTask = project.tasks.buildIOS;

        assert buildIOSTask.configuration == 'Release'
        assert buildIOSTask.bundleVersion == "1.0"
        assert buildIOSTask.bundleShortVersion == "2.6"
        assert buildIOSTask.bundleIdentifier == "com.sample.crossapp"
        assert buildIOSTask.output == "dist/CrossApp.iOS-1.0.ipa"
        assert buildIOSTask.projectFile == "src/test/resources/CrossApp/iOS/CrossApp.iOS.csproj"
        assert Paths.get(buildIOSTask.infoPlist) == Paths.get("src/test/resources/CrossApp/iOS/Info.plist")
        assert buildIOSTask.platform == "iPhone"

    }

    @Test
    public void testApplyCreatesBuildAndroidTaskWithOverridedValues() {
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'xamarin-application-plugin'

        project.application {

            configuration 'Release'
            solution 'src/test/resources/CrossApp/CrossApp.sln' // first solution file in current folder

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

        project.evaluate();

        BuildAndroidAppTask buildAndroidTask = project.tasks.buildAndroid;
        assert buildAndroidTask.configuration == 'Release'
        assert buildAndroidTask.appVersion == "2.6"
        assert buildAndroidTask.versionCode == "1.0"
        assert buildAndroidTask.packageName ==  "com.acme.crossapp"
        assert buildAndroidTask.output == "dist/my-CrossApp.Droid-2.6.apk"
        assert buildAndroidTask.projectFile == "path/to/myapp"
        assert buildAndroidTask.manifest == "path/to/manifest"
    }

    @Test
    public void testApplyCreatesBuildIOSTaskWithOverridedValues() {
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'xamarin-application-plugin'

        project.application {

            configuration 'Release'
            solution 'src/test/resources/CrossApp/CrossApp.sln' // first solution file in current folder

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

        project.evaluate();

        BuildIOSAppTask buildIOSTask = project.tasks.buildIOS;

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
    public void testDryRun() {
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'xamarin-application-plugin'

        project.application {

            dryRun = true

            configuration 'Release'
            solution 'src/test/resources/CrossApp/CrossApp.sln' // first solution file in current folder

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

        project.evaluate();

        BuildAndroidAppTask buildAndroidTask = project.tasks.buildAndroid;
        buildAndroidTask.actions.each { action -> action.execute(buildAndroidTask) }
    }
}
