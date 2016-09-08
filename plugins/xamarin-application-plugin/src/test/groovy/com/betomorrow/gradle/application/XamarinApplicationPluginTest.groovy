package com.betomorrow.gradle.application

import com.betomorrow.gradle.application.context.Context
import com.betomorrow.gradle.application.tasks.BuildAndroidAppTask
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

class XamarinApplicationPluginTest {


    @Test
    public void testApplyResolveDefaultValues() {
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'xamarin-application-plugin'


       //s Context.dryRunContext = null;

        project.application {

            configuration 'Release'
            solution 'src/test/resources/CrossApp/CrossApp.sln' // first solution file in current folder

            dryRun true

            appName 'CrossApp' // auto resolved (common part of all projects names in solution)
            appVersion '2.6' // if empty use the one defined in csproj
            storeVersion '1.0' // if empty use the one defined in csproj

            packageName "com.acme.crossapp" // if empty use the one defined in csproj

            android {
//                manifest "path/to/manifest" // auto resolved
//                output "dist/${appName}-${appVersion}.apk"  // default value
//                projectFile "path/to/myapp" // auto resolved
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
