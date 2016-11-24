package com.betomorrow.gradle.nugetpackage.extensions

import org.gradle.api.Project

/**
 * Created by olivier on 22/02/16.
 */
class NuspecPluginExtension {

    private Project project

    String authors
    String owners
    String description
    String output

    NuspecPluginExtension(Project project) {
        this.project = project
    }

}
