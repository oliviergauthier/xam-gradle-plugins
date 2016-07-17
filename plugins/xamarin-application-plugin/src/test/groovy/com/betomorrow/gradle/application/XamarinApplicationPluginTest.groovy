package com.betomorrow.gradle.application

import com.betomorrow.gradle.application.tasks.BuildAndroidAppTask
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

class XamarinApplicationPluginTest {


    @Test
    public void testApplyResolveDefaultValues() {
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'xamarin-base-plugin'
        project.apply plugin: 'xamarin-application-plugin'

        project.xamarin {
            configuration 'Release'
            solution 'CrossApp.sln'
        }

        project.application {
            appName 'CrossApp'
            appVersion '2.6'
            storeVersion '1.0'

            packageName "com.acme.crossapp"

            android {
                manifest "path/to/manifest" // auto resolved
                output "dist/${appName}-${appVersion}.apk"  // default value
                projectFile "path/to/myapp" // auto resolved
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
