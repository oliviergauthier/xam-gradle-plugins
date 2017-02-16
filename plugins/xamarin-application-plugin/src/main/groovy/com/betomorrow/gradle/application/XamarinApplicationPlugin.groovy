package com.betomorrow.gradle.application

import com.betomorrow.gradle.application.context.Context
import com.betomorrow.gradle.application.extensions.XamarinAndroidApplicationExtension
import com.betomorrow.gradle.application.extensions.XamarinApplicationExtension
import com.betomorrow.gradle.application.extensions.XamarinIosApplicationExtension
import com.betomorrow.gradle.application.tasks.BuildAndroidAppTask
import com.betomorrow.gradle.application.tasks.BuildIOSAppTask
import com.betomorrow.gradle.commons.tasks.CleanTask
import com.betomorrow.gradle.commons.tasks.GlobalVariables
import com.betomorrow.gradle.commons.tasks.Groups
import org.gradle.api.Plugin
import org.gradle.api.Project

// TODO : Handle missing solution file error
// TODO : Handle verbose mode
// TODO : Handle put version in several AssemblyInfo.cs and Solution File
class XamarinApplicationPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.with {

            GlobalVariables.initVariables(project)

            extensions.create("application", XamarinApplicationExtension, project)
            application.extensions.create("android", XamarinAndroidApplicationExtension, project)
            application.extensions.create("ios", XamarinIosApplicationExtension, project)

            afterEvaluate {

                XamarinApplicationExtension application = extensions.getByName("application")
                XamarinAndroidApplicationExtension android = application.extensions.getByName("android")
                XamarinIosApplicationExtension ios = application.extensions.getByName("ios")

                Context.configure(project.hasProperty('dryRun') && project.dryRun)

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
