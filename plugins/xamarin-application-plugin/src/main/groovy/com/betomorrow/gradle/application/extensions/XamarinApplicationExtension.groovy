package com.betomorrow.gradle.application.extensions

import org.gradle.api.Project

class XamarinApplicationExtension {

    private static String DEFAULT_CONFIGURATION = 'Release'

    private Project project

    String appName
    String appVersion
    String storeVersion
    String packageName
    String configuration
    String solution
    boolean dryRun

    XamarinApplicationExtension(Project project) {
        this.project = project
    }

    String getConfiguration() {
        if (configuration != null) {
            return configuration
        }
        return DEFAULT_CONFIGURATION
    }

    String getSolutionPath() {
        def p = project.file(solution)
        return p.absolutePath
    }




}
