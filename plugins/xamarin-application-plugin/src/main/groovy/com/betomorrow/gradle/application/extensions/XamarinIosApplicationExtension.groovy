package com.betomorrow.gradle.application.extensions

import com.betomorrow.ios.tools.plist.DefaultInfoPlistReader
import com.betomorrow.ios.tools.plist.InfoPlist
import com.betomorrow.ios.tools.plist.InfoPlistReader
import com.betomorrow.msbuild.tools.Files.FileUtils
import com.betomorrow.msbuild.tools.descriptors.project.ProjectDescriptor
import com.betomorrow.msbuild.tools.descriptors.solution.SolutionDescriptor
import com.betomorrow.msbuild.tools.descriptors.solution.SolutionLoader
import org.gradle.api.Project

import java.nio.file.Paths

class XamarinIosApplicationExtension {

    protected SolutionLoader solutionLoader = new SolutionLoader();
    protected  InfoPlistReader infoPlistReader = new DefaultInfoPlistReader();

    private static String IOS_SUFFIX = ".IOS"
    private static String DEFAULT_PLATFORM = "iPhone"

    def String appName;
    def String output;
    def String infoPlist;
    def String projectFile;
    def String platform;

    private @Lazy ProjectDescriptor project = solution.getProject(getAppName());
    private @Lazy SolutionDescriptor solution = solutionLoader.load(applicationExtension.solutionPath);
    private @Lazy InfoPlist readInfoPlist = infoPlistReader.read(getInfoPlist());

    private XamarinApplicationExtension applicationExtension;

    public XamarinIosApplicationExtension(Project project) {
        applicationExtension = project.extensions.getByType(XamarinApplicationExtension)
    }

    public String getAppName() {
        // 1. returns configured appName
        if (appName != null) {
            return appName
        }

        // 2. try to find an appName.IOS in solution file
        String defaultAppName = applicationExtension.appName + IOS_SUFFIX;
        if (solution.containsProject(defaultAppName)) {
            return  defaultAppName;
        }

        // 3. try to find a single ios project
        if (solution.hasSingleIosProject()) {
            return solution.firstIosProject.name;
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
        return "dist/${getAppName()}-${getBundleVersion()}.ipa"
    }

    public String getInfoPlist() {
        // 1. returns configured manifest
        if (infoPlist != null) {
            return infoPlist;
        }

        // 2. return manifest of projectFile
        def infoPlistPath = FileUtils.toUnixPath(project.infoPlist)
        return project.path.parent.resolve(infoPlistPath).toString()
    }


    public String getBundleVersion() {
        // 1. returns configured bundleVersion
        if (applicationExtension.appVersion != null) {
            return applicationExtension.appVersion;
        }

        // 2. returns the one in manifest
        return readInfoPlist.bundleVersion;
    }

    public String getBundleShortVersion() {
        // 1. returns configured versionCode
        if (applicationExtension.storeVersion != null) {
            return applicationExtension.storeVersion;
        }

        // 2. returns the one in manifest
        return readInfoPlist.bundleShortVersion;
    }

    public String getBundleIdentifier() {
        // 1. returns configured bundleIdentifier
        if (applicationExtension.packageName != null) {
            return applicationExtension.packageName;
        }

        // 2. returns the one in manifest
        return readInfoPlist.bundleIdentifier;
    }

    public String getPlatform() {
        // 1. returns configured platform
        if (platform != null) {
            return platform;
        }

        // 2. return default value
        return DEFAULT_PLATFORM;
    }
}
