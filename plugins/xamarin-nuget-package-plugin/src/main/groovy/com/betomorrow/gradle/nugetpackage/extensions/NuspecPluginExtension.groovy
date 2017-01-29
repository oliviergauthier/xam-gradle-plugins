package com.betomorrow.gradle.nugetpackage.extensions

import org.gradle.api.Project

class NuspecPluginExtension {

    private Project project

    String output

    String packageId
    String version
    String authors
    String owners
    String licenseUrl
    String projectUrl
    String iconUrl
    Boolean requireLicenseAcceptance
    String description
    String releaseNotes
    String copyright
    String tags


    NuspecPluginExtension(Project project) {
        this.project = project
    }

    String getOutput() {
        return "nuspec.template"
    }

}
