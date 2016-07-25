package com.betomorrow.gradle.application.extensions

import com.betomorrow.gradle.base.extensions.XamarinBaseExtension
import com.betomorrow.msbuild.tools.descriptors.solution.SolutionDescriptor
import com.betomorrow.msbuild.tools.descriptors.solution.SolutionLoader
import com.sun.javaws.exceptions.InvalidArgumentException
import org.gradle.api.Project

import java.nio.file.Paths

class XamarinAndroidApplicationExtension {

    protected SolutionLoader solutionLoader = new SolutionLoader()
    protected String DROID_SUFFIX = ".Droid"

    def String appName;
    def String output;
    def String manifest;
    def String projectFile;

    private XamarinBaseExtension baseExtension;
    private XamarinApplicationExtension applicationExtension;

    public XamarinAndroidApplicationExtension(Project project) {
        baseExtension = project.extensions.getByType(XamarinBaseExtension)
        applicationExtension = project.extensions.getByType(XamarinApplicationExtension)
    }


    public String getAppName() {
        // 1. returns configured appName
        if (appName != null) {
            return appName
        }

        def solution = solutionLoader.load(baseExtension.solution)

        // 2. try to find an appName.Droid in solution file
        String defaultAppName = applicationExtension.appName + DROID_SUFFIX;
        if (solution.containsProject(defaultAppName)) {
            return  defaultAppName;
        }

        // 3. try to find a single android project
        if (solution.hasSingleAndroidProject()) {
            return solution.firstAndroidProject.name;
        }

        throw new InvalidArgumentException("Can't resolve android project, please specify it with appName")

    }

    public String getProjectFile() {
        // 1. returns configured projectFile
        if (projectFile != null) {
            return projectFile
        }

        // 2. returns projectFile of android appName
        return getSolution().getProject(getAppName()).path
    }

    public String getOutput() {
        // 1. returns configured output
        if (output != null) {
            return output
        }

        // 2. returns default value
        return "dist/${getAppName()}-${applicationExtension.appVersion}.apk"
    }

    public String getManifest() {
        // 1. returns configured manifest
        if (manifest != null) {
            return manifest;
        }

        // 2. return manifest of projectFile
        def manifestRelativePath = getSolution().getProject(getAppName()).androidManifest.replace("\\", "/")
        return Paths.get(getProjectFile()).parent.resolve(manifestRelativePath).toString()
    }

    private SolutionDescriptor getSolution() {
        return solutionLoader.load(baseExtension.solution)
    }

}
