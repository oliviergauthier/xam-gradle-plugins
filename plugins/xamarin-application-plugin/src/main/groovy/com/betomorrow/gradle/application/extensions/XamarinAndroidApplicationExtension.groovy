package com.betomorrow.gradle.application.extensions

import com.betomorrow.xamarin.android.manifest.AndroidManifest
import com.betomorrow.xamarin.android.manifest.AndroidManifestReader
import com.betomorrow.xamarin.android.manifest.DefaultAndroidManifestReader
import com.betomorrow.xamarin.files.FileUtils
import com.betomorrow.xamarin.descriptors.project.ProjectDescriptor
import com.betomorrow.xamarin.descriptors.solution.SolutionDescriptor
import com.betomorrow.xamarin.descriptors.solution.SolutionLoader
import org.gradle.api.Project

class XamarinAndroidApplicationExtension {

    protected SolutionLoader solutionLoader = new SolutionLoader()
    protected AndroidManifestReader manifestReader = new DefaultAndroidManifestReader()
    protected String DROID_SUFFIX = ".Droid"

    @Lazy SolutionDescriptor sd = solutionLoader.load(project.file(applicationExtension.solution))
    @Lazy ProjectDescriptor pd = sd.getProject(getAppName())
    @Lazy AndroidManifest readManifest = manifestReader.read(getManifest())

    String appName
    String output
    String manifest
    String projectFile

    private Project project

    private XamarinApplicationExtension applicationExtension

    XamarinAndroidApplicationExtension(Project project) {
        this.project = project
        applicationExtension = project.extensions.getByType(XamarinApplicationExtension)
    }

    String getAppName() {
        // 1. returns configured appName
        if (appName != null) {
            return appName
        }

        // 2. try to find an appName.Droid in solution file
        String defaultAppName = applicationExtension.appName + DROID_SUFFIX
        if (sd.containsProject(defaultAppName)) {
            return  defaultAppName
        }

        // 3. try to find a single android project
        if (sd.hasSingleAndroidProject()) {
            return sd.firstAndroidProject.name
        }

        throw new IllegalArgumentException("Can't resolve android project, please specify it with appName")

    }

    String getProjectFile() {
        // 1. returns configured projectFile
        if (projectFile != null) {
            return projectFile
        }

        // 2. returns projectFile of android appName
        return pd.path
    }

    String getOutput() {
        // 1. returns configured output
        if (output != null) {
            return output
        }

        // 2. returns default value
        return "dist/${getAppName()}-${getAppVersion()}.apk"
    }

    String getManifest() {
        // 1. returns configured manifest
        if (manifest != null) {
            return manifest
        }

        // 2. return manifest of projectFile
        def manifestRelativePath = FileUtils.toUnixPath(pd.androidManifest)
        return pd.path.parent.resolve(manifestRelativePath).toString()
    }


    String getAppVersion() {
        // 1. returns configured bundleVersion
        if (applicationExtension.appVersion != null) {
            return applicationExtension.appVersion
        }

        // 2. returns the one in manifest
        return readManifest.versionName
    }

    String getStoreVersion() {
        // 1. returns configured versionCode
        if (applicationExtension.storeVersion != null) {
            return applicationExtension.storeVersion
        }

        // 2. returns the one in manifest
        return readManifest.versionCode
    }

    String getPackageName() {
        // 1. returns configured bundleIdentifier
        if (applicationExtension.packageName != null) {
            return applicationExtension.packageName
        }

        // 2. returns the one in manifest
        return readManifest.packageName
    }

}
