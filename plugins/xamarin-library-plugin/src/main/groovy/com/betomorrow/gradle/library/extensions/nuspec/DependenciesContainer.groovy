package com.betomorrow.gradle.library.extensions.nuspec

import com.betomorrow.msbuild.tools.nuspec.dependencies.Dependency
import org.gradle.api.Project

class DependenciesContainer {

    private Project project

    List<Dependency> dependencies = []

    DependenciesContainer(Project project) {
        this.project = project
    }

    Dependency dependency(String dependency) {
        def dep = new Dependency(dependency)
        dependencies.add(dep)
        return dep
    }



}
