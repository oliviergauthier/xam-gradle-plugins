package com.betomorrow.gradle.nugetpackage.extensions

import org.gradle.api.Project

/**
 * Created by olivier on 22/02/16.
 */
class NuspecPluginExtension {

    private Project project;

    def String authors;
    def String owners;
    def String description;
    def String output;

    NuspecPluginExtension(Project project) {
        this.project = project
    }

}
