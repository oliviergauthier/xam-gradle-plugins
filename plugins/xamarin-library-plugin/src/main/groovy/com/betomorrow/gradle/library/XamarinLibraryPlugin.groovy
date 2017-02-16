package com.betomorrow.gradle.library

import com.betomorrow.gradle.commons.tasks.CleanTask
import com.betomorrow.gradle.commons.tasks.GlobalVariables
import com.betomorrow.gradle.commons.tasks.Groups
import com.betomorrow.gradle.library.context.Context
import com.betomorrow.gradle.library.extensions.XamarinLibraryExtension
import com.betomorrow.gradle.library.tasks.BuildTask
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

                XamarinLibraryExtension library = extensions.getByName("library")

                Context.configure(project.hasProperty('dryRun') && project.dryRun)

                task("clean", description: "clean library", group: Groups.BUILD, 'type': CleanTask) {
                    solutionFile = library.solution
                }

                task("build", description: "build library", group: Groups.BUILD, 'type': BuildTask) {
                    solutionFile = library.solution
                    configuration = library.configuration
                }

            }
        }
    }
}
