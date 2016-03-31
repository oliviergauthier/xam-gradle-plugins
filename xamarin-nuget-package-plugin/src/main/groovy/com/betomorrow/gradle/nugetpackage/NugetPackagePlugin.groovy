package com.betomorrow.gradle.nugetpackage

import com.betomorrow.gradle.nugetpackage.extensions.AssembliesPluginExtension
import com.betomorrow.gradle.nugetpackage.extensions.DependenciesPluginExtension
import com.betomorrow.gradle.nugetpackage.extensions.NuspecPluginExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by olivier on 22/02/16.
 */
class NugetPackagePlugin implements Plugin<Project>{

    /**
     * http://stackoverflow.com/questions/28999106/define-nested-extension-containers-in-gradle
     * http://mrhaki.blogspot.fr/2013/05/gradle-goodness-extending-dsl.html
     */
    @Override
    void apply(Project project) {
        project.extensions.create("nuspec", NuspecPluginExtension, project)
        project.nuspec.extensions.create("dependencies", DependenciesPluginExtension, project)
        project.nuspec.extensions.create("assemblies", AssembliesPluginExtension, project)


    }

}
