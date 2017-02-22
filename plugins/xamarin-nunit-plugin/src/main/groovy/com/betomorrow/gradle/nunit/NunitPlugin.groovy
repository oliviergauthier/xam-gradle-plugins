package com.betomorrow.gradle.nunit

import com.betomorrow.gradle.commons.tasks.GlobalVariables
import com.betomorrow.gradle.nunit.context.PluginContext
import com.betomorrow.gradle.nunit.extensions.NunitPluginExtension
import com.betomorrow.gradle.nunit.tasks.RunNUnitConsoleTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class NunitPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        project.with {

            GlobalVariables.initVariables(project)

            extensions.create("nunit", NunitPluginExtension, project)

            afterEvaluate {

                PluginContext.configure(project)

                NunitPluginExtension nunit = extensions.getByName("nunit")

                task("test", description : "Run nunit test", group : "verification", type : RunNUnitConsoleTask) {
                    projects = nunit.projects
                    format = nunit.format
                    assemblies = nunit.assemblies
                }

            }
        }

    }
}
