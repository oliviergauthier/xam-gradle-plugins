package com.betomorrow.gradle.library

import com.betomorrow.gradle.commons.SemVersion
import com.betomorrow.gradle.commons.tasks.CleanDistTask
import com.betomorrow.gradle.commons.tasks.CleanTask
import com.betomorrow.gradle.commons.tasks.GlobalVariables
import com.betomorrow.gradle.commons.tasks.Groups
import com.betomorrow.gradle.library.context.PluginContext
import com.betomorrow.gradle.library.extensions.publish.PublishLocalPluginExtension
import com.betomorrow.gradle.library.extensions.publish.PublishPluginExtension

import com.betomorrow.gradle.library.extensions.nuspec.NuspecPluginExtension
import com.betomorrow.gradle.library.extensions.XamarinLibraryExtension
import com.betomorrow.gradle.library.extensions.publish.PublishRemoteExtension
import com.betomorrow.gradle.library.tasks.BuildTask
import com.betomorrow.gradle.library.tasks.GenerateNuspecTask
import com.betomorrow.gradle.library.tasks.InstallPackageTask
import com.betomorrow.gradle.library.tasks.NugetRestoreTask
import com.betomorrow.gradle.library.tasks.PackageLibraryTask
import com.betomorrow.gradle.library.tasks.PushPackageTask
import org.gradle.api.Plugin
import org.gradle.api.Project


class XamarinLibraryPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.with {

            GlobalVariables.initVariables(project)

            extensions.create("library", XamarinLibraryExtension, project)

            extensions.create("nuspec", NuspecPluginExtension, project)

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

                task("nugetRestore", description: "restore nuget packages", group: Groups.BUILD, overwrite : true,  'type' : NugetRestoreTask){}

                task("build", description: "build library",  group: Groups.BUILD, 'type': BuildTask) {
                    version = SemVersion.parse(project.version).versionNumber
                    solutionFile = library.solution
                    configuration = library.configuration
                }

                // Package

                NuspecPluginExtension nuspec = extensions.getByName("nuspec")

                def packageTasks = []
                def installTasks = []
                def deployTasks = []
                def generateNuspecTasks = []

                nuspec.packages.all { p ->

                    def targetNuspecPath = project.file("${p.packageId}.nuspec").path

                    def generateNuspec = task("generateNuspec${p.name}", 'type': GenerateNuspecTask) {
                        packageId = p.packageId
                        title = p.title
                        version = p.version
                        authors = p.authors
                        owners = p.owners
                        licenseUrl = p.licenseUrl
                        projectUrl = p.projectUrl
                        iconUrl = p.iconUrl
                        requireLicenseAcceptance = p.requireLicenseAcceptance
                        description = p.description
                        releaseNotes = p.releaseNotes
                        copyright = p.copyright
                        tags = p.tags
                        output = targetNuspecPath
                        dependencies = p.dependencies
                        assemblies = p.assemblies
                        configuration = library.configuration
                    }
                    generateNuspecTasks.add(generateNuspec)

                    def packageTask = task("package${p.name}", dependsOn: ['build', "generateNuspec${p.name}"], 'type': PackageLibraryTask) {
                        nuspecPath = targetNuspecPath
                        suffix = p.suffix
                        outputDirectory = NuspecPluginExtension.OUTPUT_DIRECTORY
                    }
                    packageTasks.add(packageTask)

                    // Deploy

                    def installTask = task("install${p.name}", dependsOn: ["package${p.name}"], 'type': InstallPackageTask) {
                        packagePath = p.output
                        source = publish.local.path
                        format = publish.local.format
                    }
                    installTasks.add(installTask)

                    def deployTask = task("deploy${p.name}", dependsOn: ["install${p.name}"], 'type': PushPackageTask) {
                        packagePath = p.output
                        source = publish.remote.url
                        apiKey = publish.remote.apiKey
                    }
                    deployTasks.add(deployTask)
                }

                task("generateNuspec", dependsOn: generateNuspecTasks, description: "Generate nuspec files", group: Groups.PACKAGE) {}
                task("package", dependsOn: packageTasks, description: " Packages libraries with nuget", group: Groups.PACKAGE) {}
                task("install", dependsOn: installTasks, description: "Install libraries locally", group: Groups.DEPLOY) {}
                task("deploy", dependsOn: deployTasks, description: "Deploy libraries on remote server", group: Groups.DEPLOY) {}

            }
        }

    }

}
