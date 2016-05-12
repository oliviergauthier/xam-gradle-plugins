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

    def String source
    def String output

    def String packageId
    def String version
    def String authors
    def String owners
    def String licenseUrl
    def String projectUrl
    def String iconUrl
    def Boolean requireLicenseAcceptance
    def String description
    def String releaseNotes
    def String copyright
    def String tags


    def DependencySet dependencies;
    def AssemblySet assemblies;

    @TaskAction
    public void generateNuspec() {
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
