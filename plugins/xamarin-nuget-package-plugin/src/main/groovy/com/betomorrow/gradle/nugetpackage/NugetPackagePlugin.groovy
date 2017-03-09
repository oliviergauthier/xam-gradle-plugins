package com.betomorrow.gradle.nugetpackage

import com.betomorrow.gradle.commons.tasks.GlobalVariables
import com.betomorrow.gradle.commons.tasks.Groups
import com.betomorrow.gradle.nugetpackage.context.PluginContext
import com.betomorrow.gradle.nugetpackage.extensions.AssembliesPluginExtension
import com.betomorrow.gradle.nugetpackage.extensions.DependenciesPluginExtension
import com.betomorrow.gradle.nugetpackage.extensions.NuspecPluginExtension
import com.betomorrow.gradle.nugetpackage.tasks.GenerateNuspecTask
import com.betomorrow.gradle.nugetpackage.tasks.PushPackageTask
import com.betomorrow.gradle.nugetpackage.tasks.PackageLibraryTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class NugetPackagePlugin implements Plugin<Project>{

    private static final String NUSPEC_PATH = "generated.nuspec"

    /**
     * http://stackoverflow.com/questions/28999106/define-nested-extension-containers-in-gradle
     * http://mrhaki.blogspot.fr/2013/05/gradle-goodness-extending-dsl.html
     */
    @Override
    void apply(Project project) {

        project.with {

            GlobalVariables.initVariables(project)

            extensions.create("nuspec", NuspecPluginExtension, project)
            nuspec.extensions.create("dependencies", DependenciesPluginExtension, project)
            nuspec.extensions.create("assemblies", AssembliesPluginExtension, project)

            afterEvaluate {

                PluginContext.configure(project)

                NuspecPluginExtension nuspec = extensions.getByName("nuspec")

                task("generateNuspec", description: "generate nuspec file", group: Groups.BUILD, 'type': GenerateNuspecTask) {
                    packageId = nuspec.packageId
                    version = nuspec.version
                    authors =  nuspec.authors
                    owners = nuspec.owners
                    licenseUrl = nuspec.licenseUrl
                    projectUrl = nuspec.projectUrl
                    iconUrl = nuspec.iconUrl
                    requireLicenseAcceptance = nuspec.requireLicenseAcceptance
                    description = nuspec.description
                    releaseNotes = nuspec.releaseNotes
                    copyright = nuspec.copyright
                    tags = nuspec.tags

                    output = project.file(NUSPEC_PATH).absolutePath
                    dependencies = nuspec.dependencies.dependencies
                    assemblies = nuspec.assemblies.assemblies
                }

                task("package", description: "Package lib with Nuget", dependsOn: ['generateNuspec'], group:Groups.PACKAGE, 'type': PackageLibraryTask) {
                    nuspecPath = project.file(NUSPEC_PATH).absolutePath
                    suffix = nuspec.suffix
                }

                task("install", description: "Install package locally", dependsOn: ['package'], group:Groups.DEPLOY, 'type' : PushPackageTask) {
                    packagePath = nuspec.output
                    source = nuspec.localRepository
                }

                task("deploy", description: "Deploy package on remote server", dependsOn: ['package'], group:Groups.DEPLOY, 'type' : PushPackageTask) {
                    packagePath = nuspec.output
                    source = nuspec.remoteRepository
                    apiKey = nuspec.apiKey
                }
            }
        }
    }

}
