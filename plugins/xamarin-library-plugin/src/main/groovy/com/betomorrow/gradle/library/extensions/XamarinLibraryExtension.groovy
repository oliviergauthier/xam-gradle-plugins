package com.betomorrow.gradle.library.extensions

import org.gradle.api.Project

class XamarinLibraryExtension {

    private static String DEFAULT_CONFIGURATION = 'Release'

    private Project project

    String solution
    String configuration
    boolean dryRun

    XamarinLibraryExtension(Project project) {
        this.project = project
    }

    String getConfiguration() {
        return project.configuration
    }

    String getSolution() {
        return project.solution
    }

}
