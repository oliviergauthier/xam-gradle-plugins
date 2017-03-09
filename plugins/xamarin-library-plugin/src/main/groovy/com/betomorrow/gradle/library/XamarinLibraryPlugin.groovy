package com.betomorrow.gradle.library

import com.betomorrow.gradle.commons.tasks.CleanDistTask
import com.betomorrow.gradle.commons.tasks.CleanTask
import com.betomorrow.gradle.commons.tasks.GlobalVariables
import com.betomorrow.gradle.commons.tasks.Groups
import com.betomorrow.gradle.library.context.PluginContext
import com.betomorrow.gradle.library.extensions.XamarinLibraryExtension
import com.betomorrow.gradle.library.tasks.BuildTask
import com.betomorrow.gradle.library.tasks.NugetRestoreTask
import org.gradle.api.Plugin
import org.gradle.api.Project


// TODO : Handle missing solution file error
// TODO : Handle verbose mode
// TODO : Handle put version in several AssemblyInfo.cs and Solution File
class XamarinLibraryPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.with {

            GlobalVariables.initVariables(project)

            extensions.create("library", XamarinLibraryExtension, project)

            afterEvaluate {

                PluginContext.configure(project)

                XamarinLibraryExtension library = extensions.getByName("library")

                task("clean", description: "clean library", group: Groups.BUILD, 'type': CleanTask) {
                    solutionFile = library.solution
                }
                task("cleanDist", description: "clean dist directory", group: Groups.BUILD, 'type': CleanDistTask) {}

                task("cleanAll", description: "clean all dist and library", group: Groups.BUILD, dependsOn: ['clean', 'cleanDist']) {}

                task("nugetRestore", description: "restore nuget packages", group: Groups.BUILD, 'type' : NugetRestoreTask){}

                task("build", description: "build library", dependsOn: ["nugetRestore"],  group: Groups.BUILD, 'type': BuildTask) {
                    solutionFile = library.solution
                    configuration = library.configuration
                }

            }
        }
    }
}
