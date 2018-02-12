package com.betomorrow.gradle.library.extensions.nuspec

import com.betomorrow.gradle.commons.SemVersion
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project

class NuspecPluginExtension {

    public static final String OUTPUT_DIRECTORY = "dist"

    private Project project

    NamedDomainObjectContainer<NuspecItemExtension> packages

    String title
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
    String suffix

    NuspecPluginExtension(Project project) {
        this.project = project
        packages = project.container(NuspecItemExtension)
        packages.all {
            it.project = project
            it.parent = this
            it.dependencies = project.container(DependenciesExtension)
        }
    }

    def packages(Closure closure) {
        packages.configure(closure)
    }

    String getVersion() {
        return version ?: SemVersion.parse(project.version).versionNumber
    }

    String getSuffix() {
        return suffix ?: SemVersion.parse(project.version).suffix
    }

}
