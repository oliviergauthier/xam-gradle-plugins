package com.betomorrow.gradle.library.extensions

import com.betomorrow.msbuild.tools.nuspec.dependencies.Dependency
import org.gradle.api.Project

class DependenciesPluginExtension {

    private Project project

    List<Dependency> dependencies = []

    DependenciesPluginExtension(Project project) {
        this.project = project
    }

    Dependency dependency(String dependency) {
        def dep = new Dependency(dependency)
        dependencies.add(dep)
        return dep
    }



}
