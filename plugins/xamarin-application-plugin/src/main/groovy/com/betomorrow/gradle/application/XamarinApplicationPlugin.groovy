package com.betomorrow.gradle.application

import com.betomorrow.gradle.application.extensions.XamarinAndroidApplicationExtension
import com.betomorrow.gradle.application.extensions.XamarinApplicationExtension
import com.betomorrow.gradle.application.extensions.XamarinIosApplicationExtension
import com.betomorrow.gradle.application.tasks.BuildAndroidAppTask
import com.betomorrow.gradle.base.extensions.XamarinBaseExtension
import com.betomorrow.msbuild.tools.commands.DefaultCommandRunner
import org.gradle.api.Plugin
import org.gradle.api.Project

class XamarinApplicationPlugin implements Plugin<Project> {

    static final String BUILD_GROUP = "build"

    @Override
    void apply(Project project) {
        project.with {

            extensions.create("application", XamarinApplicationExtension)
            application.extensions.create("android", XamarinAndroidApplicationExtension, project)
            application.extensions.create("ios", XamarinIosApplicationExtension)

            afterEvaluate {

                XamarinApplicationExtension application = extensions.getByName("application");
                XamarinAndroidApplicationExtension android = application.extensions.getByName("android");
                XamarinIosApplicationExtension ios = application.extensions.getByName("ios");

                DefaultCommandRunner.INSTANCE.dryRun = application.dryRun

                task("buildAndroid", description: "build android application", group: BUILD_GROUP, type: BuildAndroidAppTask) {
                    appVersion = application.appVersion
                    storeVersion = application.storeVersion
                    packageName = application.packageName
                    projectFile = android.projectFile
                    manifest = android.manifest
                    output = android.output
                    configuration = application.configuration
                }
            }
        }
    }

}
