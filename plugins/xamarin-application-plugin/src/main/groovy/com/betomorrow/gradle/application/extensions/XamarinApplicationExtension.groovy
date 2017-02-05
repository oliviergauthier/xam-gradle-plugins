package com.betomorrow.gradle.application.extensions

import org.gradle.api.Project

class XamarinApplicationExtension {

    private Project project

    String appName
    String appVersion
    String storeVersion
    String packageName
    boolean dryRun

    XamarinApplicationExtension(Project project) {
        this.project = project
    }

    String getConfiguration() {
       return project.configuration
    }

    String getSolution() {
        return project.solution
    }

}
