package com.betomorrow.gradle.application

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

class XamarinApplicationPluginTest {


    @Test
    public void testApplyResolveDefaultValues() {
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'xamarin-application-plugin'

//        project.xamarin {
//            configuration 'Release'
//            solution 'CrossApp.sln'
//        }

        project.application {
            appName 'CrossApp'
            appVersion '2.6'
            storeVersion '1.0'

            packageName "com.acme.crossapp"

            android {
               // manifest "path/to/manifest" // auto resolved
                output "dist/{appName}-{appVersion}.apk"  // default value
            }

            ios {
                output "dist/{appName}-{appVersion}.ipa"  // default value
            }
        }

    }
}
