package com.betomorrow.gradle.application.extensions

import org.gradle.api.Project

class XamarinApplicationExtension {

    private static String DEFAULT_CONFIGURATION = 'Release'

    private Project project;

    def String appName;
    def String appVersion;
    def String storeVersion;
    def String packageName;
    def String configuration;
    def String solution;
    def boolean dryRun;

    public XamarinApplicationExtension(Project project) {
        this.project = project;
    }

    public String getConfiguration() {
        if (configuration != null) {
            return configuration
        }
        return DEFAULT_CONFIGURATION
    }

    public String getSolutionPath() {
        return project.file(solution).absolutePath;
    }




}
