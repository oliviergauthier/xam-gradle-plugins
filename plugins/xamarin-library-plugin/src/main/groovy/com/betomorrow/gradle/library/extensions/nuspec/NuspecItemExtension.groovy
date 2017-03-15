package com.betomorrow.gradle.library.extensions.nuspec

import com.betomorrow.msbuild.tools.nuspec.dependencies.Dependency
import org.gradle.api.Project

import java.nio.file.Paths

class NuspecItemExtension {

    private String name

    Project project
    List<AssemblyTarget> assemblies = []
    List<Dependency> dependencies = []

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
    String suffix

    NuspecItemExtension(String name) {
        this.name = name
    }

    String getOutput() {
        return Paths.get(NuspecPluginExtension.OUTPUT_DIRECTORY).resolve(getGeneratedPackageName()).toString()
    }

    String getGeneratedPackageName() {
        String baseName = "${getPackageId()}.${getVersion()}"
        if (suffix) {
            baseName = "${baseName}-${suffix}"
        }
        return "${baseName}.nupkg"
    }

    String getPackageId() {
        if (packageId) {
            return packageId
        }
        if (project.hasProperty("name")) {
            return project.name
        }
        return project.name
    }

    String getVersion() {
        if (version) {
            return version
        }

        return project.version
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
