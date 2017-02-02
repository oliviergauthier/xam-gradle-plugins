package com.betomorrow.gradle.library

import com.betomorrow.gradle.commons.tasks.CleanTask
import com.betomorrow.gradle.commons.tasks.GlobalVariables
import com.betomorrow.gradle.commons.tasks.Groups
import com.betomorrow.gradle.library.context.Context
import com.betomorrow.gradle.library.extensions.XamarinLibraryExtension
import com.betomorrow.gradle.library.tasks.BuildTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class XamarinLibraryPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.with {

            GlobalVariables.initVariables(project)

            extensions.create("library", XamarinLibraryExtension, project)

            afterEvaluate {

                XamarinLibraryExtension library = extensions.getByName("library")

                Context.configure(library.dryRun)

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
