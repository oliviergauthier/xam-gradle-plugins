package com.betomorrow.gradle.nugetpackage.tasks

import com.betomorrow.msbuild.tools.nuspec.NuSpec
import com.betomorrow.msbuild.tools.nuspec.assemblies.AssemblySet
import com.betomorrow.msbuild.tools.nuspec.dependencies.DependencySet
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Created by olivier on 26/02/16.
 */
class GenerateNuspec extends DefaultTask {

    String source
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


    DependencySet dependencies
    AssemblySet assemblies

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
        nuSpec.dependencySet = dependencies
        nuSpec.assemblySet = assemblies

        nuSpec.process()
    }
}
