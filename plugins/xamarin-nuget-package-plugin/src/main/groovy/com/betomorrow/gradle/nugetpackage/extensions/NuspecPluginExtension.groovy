package com.betomorrow.gradle.nugetpackage.extensions

import org.gradle.api.Project

class NuspecPluginExtension {

    private Project project

    // Generate Nuspec
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

    // Package
    String packageName
    String suffix

    // Install / Deploy
    String localRepository
    String remoteRepository
    String apiKey


    NuspecPluginExtension(Project project) {
        this.project = project
    }

    String getOutput() {
        return "nuspec.template"
    }

    String getPackageName() {
        if (packageName) {
            return packageName
        }
        return packageId
    }

}
