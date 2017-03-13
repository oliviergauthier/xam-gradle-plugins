package com.betomorrow.gradle.library

import com.betomorrow.gradle.commons.tasks.CleanDistTask
import com.betomorrow.gradle.commons.tasks.CleanTask
import com.betomorrow.gradle.commons.tasks.GlobalVariables
import com.betomorrow.gradle.commons.tasks.Groups
import com.betomorrow.gradle.library.context.PluginContext
import com.betomorrow.gradle.library.extensions.nuspec.AssembliesPluginExtension
import com.betomorrow.gradle.library.extensions.nuspec.DependenciesPluginExtension
import com.betomorrow.gradle.library.extensions.publish.PublishLocalPluginExtension
import com.betomorrow.gradle.library.extensions.publish.PublishPluginExtension
import com.betomorrow.gradle.library.extensions.publish.PublishRemoteExtension
import com.betomorrow.gradle.library.extensions.nuspec.NuspecPluginExtension
import com.betomorrow.gradle.library.extensions.XamarinLibraryExtension
import com.betomorrow.gradle.library.tasks.BuildTask
import com.betomorrow.gradle.library.tasks.GenerateNuspecTask
import com.betomorrow.gradle.library.tasks.InstallPackageTask
import com.betomorrow.gradle.library.tasks.NugetRestoreTask
import com.betomorrow.gradle.library.tasks.PackageLibraryTask
import com.betomorrow.gradle.library.tasks.PushPackageTask
import org.gradle.api.Plugin
import org.gradle.api.Project


class XamarinLibraryPlugin implements Plugin<Project> {

    private static final String NUSPEC_PATH = "generated.nuspec"

    @Override
    void apply(Project project) {
        project.with {

            GlobalVariables.initVariables(project)

            extensions.create("library", XamarinLibraryExtension, project)

            extensions.create("nuspec", NuspecPluginExtension, project)
            nuspec.extensions.create("dependencies", DependenciesPluginExtension, project)
            nuspec.extensions.create("assemblies", AssembliesPluginExtension, project)

            extensions.create("publish", PublishPluginExtension)
            publish.extensions.create("local", PublishLocalPluginExtension)
            publish.extensions.create("remote", PublishRemoteExtension)

            afterEvaluate {

                PluginContext.configure(project)

                // Library

                task("clean", description: "clean library", group: Groups.BUILD, 'type': CleanTask) {
                    solutionFile = library.solution
                }
                task("cleanDist", description: "clean dist directory", group: Groups.BUILD, 'type': CleanDistTask) {}

                task("cleanAll", description: "clean all dist and library", group: Groups.BUILD, dependsOn: ['clean', 'cleanDist']) {}

                task("nugetRestore", description: "restore nuget packages", group: Groups.BUILD, 'type' : NugetRestoreTask){}

                task("build", description: "build library", dependsOn: ["nugetRestore"],  group: Groups.BUILD, 'type': BuildTask) {
                    version = project.version.toString()
                    solutionFile = library.solution
                    configuration = library.configuration
                }

                // Package

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

                task("package", description: "Package lib with Nuget", dependsOn: ['build', 'generateNuspec'], group:Groups.PACKAGE, 'type': PackageLibraryTask) {
                    nuspecPath = project.file(NUSPEC_PATH).absolutePath
                    suffix = nuspec.suffix
                    outputDirectory = NuspecPluginExtension.OUTPUT_DIRECTORY
                }

                // Deploy

                task("install", description: "Install package locally", dependsOn: ['package'], group:Groups.DEPLOY, 'type' : InstallPackageTask) {
                    packagePath = nuspec.output
                    source = publish.local.path
                    format = publish.local.format
                }

                task("deploy", description: "Deploy package on remote server", dependsOn: ['package'], group:Groups.DEPLOY, 'type' : PushPackageTask) {
                    packagePath = nuspec.output
                    source = publish.remote.url
                    apiKey = publish.remote.apiKey
                }
            }
        }
    }
}
