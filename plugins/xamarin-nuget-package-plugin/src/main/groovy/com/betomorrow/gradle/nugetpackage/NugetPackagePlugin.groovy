package com.betomorrow.gradle.nugetpackage

import com.betomorrow.gradle.commons.tasks.Groups
import com.betomorrow.gradle.nugetpackage.extensions.AssembliesPluginExtension
import com.betomorrow.gradle.nugetpackage.extensions.DependenciesPluginExtension
import com.betomorrow.gradle.nugetpackage.extensions.NuspecPluginExtension
import com.betomorrow.gradle.nugetpackage.tasks.GenerateNuspec
import org.gradle.api.Plugin
import org.gradle.api.Project

class NugetPackagePlugin implements Plugin<Project>{

    /**
     * http://stackoverflow.com/questions/28999106/define-nested-extension-containers-in-gradle
     * http://mrhaki.blogspot.fr/2013/05/gradle-goodness-extending-dsl.html
     */
    @Override
    void apply(Project project) {

        project.with {

            extensions.create("nuspec", NuspecPluginExtension, project)
            nuspec.extensions.create("dependencies", DependenciesPluginExtension, project)
            nuspec.extensions.create("assemblies", AssembliesPluginExtension, project)

            afterEvaluate {

                NuspecPluginExtension nuspec = extensions.getByName("nuspec")

                task("generateNuspec", description: "generate nuspec file", group: Groups.BUILD, 'type': GenerateNuspec) {
                    packageId = nuspec.packageId
                    version = nuspec.version
                    authors =  nuspec.authors
                    owners = nuspec.owners
                    description = nuspec.description
                    output = nuspec.output
                }

            }
        }
    }

}
