package com.betomorrow.gradle.library.extensions.nuspec

import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project

class NuspecPluginExtension {

    public static final String OUTPUT_DIRECTORY = "dist"

    private Project project

    NamedDomainObjectContainer<NuspecItemExtension> packages

    NuspecPluginExtension(Project project) {
        this.project = project
        packages = project.container(NuspecItemExtension)
        packages.all {
            it.project = project
        }
    }

    def packages(Closure closure) {
        packages.configure(closure)
    }

}
