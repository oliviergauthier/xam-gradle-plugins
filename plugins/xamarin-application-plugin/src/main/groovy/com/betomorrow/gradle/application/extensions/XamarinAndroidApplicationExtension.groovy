package com.betomorrow.gradle.application.extensions

import com.betomorrow.android.tools.manifest.AndroidManifest
import com.betomorrow.android.tools.manifest.AndroidManifestReader
import com.betomorrow.android.tools.manifest.DefaultAndroidManifestReader
import com.betomorrow.msbuild.tools.Files.FileUtils
import com.betomorrow.msbuild.tools.descriptors.project.ProjectDescriptor
import com.betomorrow.msbuild.tools.descriptors.solution.SolutionDescriptor
import com.betomorrow.msbuild.tools.descriptors.solution.SolutionLoader
import org.gradle.api.Project

import java.nio.file.Paths

class XamarinAndroidApplicationExtension {

    protected SolutionLoader solutionLoader = new SolutionLoader();
    protected AndroidManifestReader manifestReader = new DefaultAndroidManifestReader();
    protected String DROID_SUFFIX = ".Droid"

    @Lazy ProjectDescriptor project = solution.getProject(getAppName());
    @Lazy SolutionDescriptor solution = solutionLoader.load(applicationExtension.solution);
    @Lazy AndroidManifest readManifest = manifestReader.read(getManifest());

    def String appName;
    def String output;
    def String manifest;
    def String projectFile;

    private XamarinApplicationExtension applicationExtension;

    public XamarinAndroidApplicationExtension(Project project) {
        applicationExtension = project.extensions.getByType(XamarinApplicationExtension)
    }

    public String getAppName() {
        // 1. returns configured appName
        if (appName != null) {
            return appName
        }

        // 2. try to find an appName.Droid in solution file
        String defaultAppName = applicationExtension.appName + DROID_SUFFIX;
        if (solution.containsProject(defaultAppName)) {
            return  defaultAppName;
        }

        // 3. try to find a single android project
        if (solution.hasSingleAndroidProject()) {
            return solution.firstAndroidProject.name;
        }

        throw new IllegalArgumentException("Can't resolve android project, please specify it with appName")

    }

    public String getProjectFile() {
        // 1. returns configured projectFile
        if (projectFile != null) {
            return projectFile
        }

        // 2. returns projectFile of android appName
        return project.path
    }

    public String getOutput() {
        // 1. returns configured output
        if (output != null) {
            return output
        }

        // 2. returns default value
        return "dist/${getAppName()}-${getAppVersion()}.apk"
    }

    public String getManifest() {
        // 1. returns configured manifest
        if (manifest != null) {
            return manifest;
        }

        // 2. return manifest of projectFile
        def manifestRelativePath = FileUtils.toUnixPath(project.androidManifest)
        return Paths.get(project.path).parent.resolve(manifestRelativePath).toString()
    }


    public String getAppVersion() {
        // 1. returns configured bundleVersion
        if (applicationExtension.appVersion != null) {
            return applicationExtension.appVersion;
        }

        // 2. returns the one in manifest
        return readManifest.versionName;
    }

    public String getStoreVersion() {
        // 1. returns configured versionCode
        if (applicationExtension.storeVersion != null) {
            return applicationExtension.storeVersion;
        }

        // 2. returns the one in manifest
        return readManifest.versionCode;
    }

    public String getPackageName() {
        // 1. returns configured bundleIdentifier
        if (applicationExtension.packageName != null) {
            return applicationExtension.packageName;
        }

        // 2. returns the one in manifest
        return readManifest.packageName;
    }

}
