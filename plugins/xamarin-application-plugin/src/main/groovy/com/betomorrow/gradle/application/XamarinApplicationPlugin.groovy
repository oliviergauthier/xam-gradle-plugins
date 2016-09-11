package com.betomorrow.gradle.application

import com.betomorrow.gradle.application.context.Context
import com.betomorrow.gradle.application.extensions.XamarinAndroidApplicationExtension
import com.betomorrow.gradle.application.extensions.XamarinApplicationExtension
import com.betomorrow.gradle.application.extensions.XamarinIosApplicationExtension
import com.betomorrow.gradle.application.tasks.BuildAndroidAppTask
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

                Context.configure(application.dryRun);

                task("buildAndroid", description: "build android application", group: BUILD_GROUP, type: BuildAndroidAppTask) {
                    appVersion = android.appVersion
                    storeVersion = android.storeVersion
                    packageName = android.packageName
                    projectFile = android.projectFile
                    manifest = android.manifest
                    output = android.output
                    configuration = application.configuration
                }
            }
        }
    }

}
