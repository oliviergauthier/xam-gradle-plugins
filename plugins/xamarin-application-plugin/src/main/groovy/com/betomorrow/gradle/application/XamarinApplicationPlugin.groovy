package com.betomorrow.gradle.application

import com.betomorrow.gradle.application.context.Context
import com.betomorrow.gradle.application.extensions.XamarinAndroidApplicationExtension
import com.betomorrow.gradle.application.extensions.XamarinApplicationExtension
import com.betomorrow.gradle.application.extensions.XamarinIosApplicationExtension
import com.betomorrow.gradle.application.tasks.BuildAndroidAppTask
import com.betomorrow.gradle.application.tasks.BuildIOSAppTask
import com.betomorrow.gradle.commons.tasks.CleanTask
import com.betomorrow.gradle.commons.tasks.Groups
import org.gradle.api.Plugin
import org.gradle.api.Project

class XamarinApplicationPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.with {

            extensions.create("application", XamarinApplicationExtension, project)
            application.extensions.create("android", XamarinAndroidApplicationExtension, project)
            application.extensions.create("ios", XamarinIosApplicationExtension, project)

            afterEvaluate {

                XamarinApplicationExtension application = extensions.getByName("application");
                XamarinAndroidApplicationExtension android = application.extensions.getByName("android");
                XamarinIosApplicationExtension ios = application.extensions.getByName("ios");

                Context.configure(application.dryRun);

                task("clean", description: "clean application", group: Groups.BUILD, 'type': CleanTask) {
                    solutionFile = application.solution
                }

                task("buildAndroid", description: "build android application", group: Groups.BUILD, type: BuildAndroidAppTask) {
                    appVersion = android.appVersion
                    versionCode = android.storeVersion
                    packageName = android.packageName
                    projectFile = android.projectFile
                    manifest = android.manifest
                    output = android.output
                    configuration = application.configuration
                }

                task("buildIOS", description: "build ios application", group: Groups.BUILD, type: BuildIOSAppTask) {
                    bundleVersion = ios.bundleVersion
                    bundleShortVersion = ios.bundleShortVersion
                    bundleIdentifier = ios.bundleIdentifier
                    projectFile = ios.projectFile
                    infoPlist = ios.infoPlist
                    output = ios.output
                    configuration = application.configuration
                    solutionFile = application.solution
                    platform = ios.platform
                }

            }
        }
    }

}
