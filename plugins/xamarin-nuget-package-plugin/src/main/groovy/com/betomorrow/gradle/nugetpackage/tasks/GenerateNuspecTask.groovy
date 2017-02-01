package com.betomorrow.gradle.nugetpackage.tasks

import com.betomorrow.gradle.nugetpackage.extensions.AssemblyTarget
import com.betomorrow.msbuild.tools.nuspec.NuSpec
import com.betomorrow.msbuild.tools.nuspec.assemblies.Assembly
import com.betomorrow.msbuild.tools.nuspec.dependencies.Dependency
import com.betomorrow.msbuild.tools.nuspec.dependencies.DependencySet
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class GenerateNuspecTask extends DefaultTask {

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

    List<Dependency> dependencies
    List<AssemblyTarget> assemblies

    @TaskAction
     void generateNuspec() {
        NuSpec nuSpec = new NuSpec()

        nuSpec.output = output

        nuSpec.packageId = packageId
        nuSpec.version = version
        nuSpec.authors = authors
        nuSpec.owners = owners
        nuSpec.licenseUrl = licenseUrl
        nuSpec.projectUrl = projectUrl
        nuSpec.iconUrl = iconUrl
        nuSpec.requireLicenseAcceptance = requireLicenseAcceptance
        nuSpec.description = description
        nuSpec.releaseNotes = releaseNotes
        nuSpec.copyright = copyright
        nuSpec.tags = tags
        nuSpec.dependencySet = new DependencySet(dependencies)

        assemblies.each { target ->
            target.includes.each {
                nuSpec.assemblySet.add(new Assembly(it, target.dest))
            }
        }

        nuSpec.process()
    }
}
