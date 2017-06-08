package com.betomorrow.gradle.library.extensions.nuspec

import com.betomorrow.xamarin.tools.nuspec.dependencies.Dependency
import org.gradle.api.Project

import java.nio.file.Paths

class NuspecItemExtension {

    private String name

    Project project
    NuspecPluginExtension parent

    List<AssemblyTarget> assemblies = []
    List<Dependency> dependencies = []

    // Generate Nuspec
    String title
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
    String suffix

    NuspecItemExtension(String name) {
        this.name = name
    }

    String getOutput() {
        return Paths.get(NuspecPluginExtension.OUTPUT_DIRECTORY).resolve(getGeneratedPackageName()).toString()
    }

    String getGeneratedPackageName() {
        String baseName = "${getPackageId()}.${getVersion()}"
        if (getSuffix()) {
            baseName = "${baseName}-${getSuffix()}"
        }
        return "${baseName}.nupkg"
    }

    String getTitle() {
        if (title) {
            return title
        }

        if (parent.title) {
            return parent.title
        }

        return getPackageId()
    }

    String getPackageId() {
        return packageId ?: name
    }

    String getVersion() {
        return version ?: parent.version
    }

    String getSuffix() {
        return suffix ?: parent.suffix
    }

    String getAuthors() {
        return authors ?: parent.authors
    }

    String getOwners() {
        return owners ?: parent.owners
    }

    String getLicenseUrl() {
        return licenseUrl ?: parent.licenseUrl
    }

    String getProjectUrl() {
        return projectUrl ?: parent.projectUrl
    }

    String getIconUrl() {
        return iconUrl ?: parent.iconUrl
    }

    Boolean getRequireLicenseAcceptance() {
        return requireLicenseAcceptance ?: parent.requireLicenseAcceptance
    }

    String getDescription() {
        return description ?: parent.description
    }

    String getReleaseNotes() {
        return releaseNotes ?: parent.releaseNotes
    }

    String getCopyright() {
        return copyright ?: parent.copyright
    }

    String getTags() {
        return tags ?: parent.tags
    }

    void assemblies(Closure closure) {
        def tmp = (AssembliesContainer)project.configure(new AssembliesContainer(project), closure)
        assemblies = tmp.assemblies
    }

    void dependencies(Closure closure) {
        def tmp = (DependenciesContainer)project.configure(new DependenciesContainer(project), closure)
        dependencies = tmp.dependencies
    }


}
