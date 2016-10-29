package com.betomorrow.gradle.library

import com.betomorrow.gradle.commons.tasks.CleanTask
import com.betomorrow.gradle.commons.tasks.Groups
import com.betomorrow.gradle.library.extensions.XamarinLibraryExtension
import com.betomorrow.gradle.library.tasks.BuildTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class XamarinLibraryPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.with {

            extensions.create("library", XamarinLibraryPlugin)

            afterEvaluate {

                XamarinLibraryExtension library = extensions.getByName("library");

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
