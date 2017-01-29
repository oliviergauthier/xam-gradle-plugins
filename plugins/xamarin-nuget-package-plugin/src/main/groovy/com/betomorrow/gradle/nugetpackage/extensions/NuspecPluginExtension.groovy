package com.betomorrow.gradle.nugetpackage.extensions

import org.gradle.api.Project

class NuspecPluginExtension {

    private Project project

    String packageId
    String version
    String authors
    String owners
    String description
    String output

    NuspecPluginExtension(Project project) {
        this.project = project
    }

    String getOutput() {
        return "nuspec.template"
    }

    String getPackageId() {
        return "CrossLib.Sample"
    }

    String getVersion() {
        return "1.0.1"
    }

}
