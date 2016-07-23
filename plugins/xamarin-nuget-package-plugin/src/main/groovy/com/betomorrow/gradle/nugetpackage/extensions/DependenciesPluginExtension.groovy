package com.betomorrow.gradle.nugetpackage.extensions

import com.betomorrow.msbuild.tools.nuspec.dependencies.Dependency
import org.gradle.api.Project

/**
 * Created by olivier on 23/02/16.
 */
class DependenciesPluginExtension {

    private Project project;

    List<Dependency> dependencies = []

    DependenciesPluginExtension(Project project) {
        this.project = project
    }

    public Dependency dependency(String dependency) {
        def dep = new Dependency(dependency)
        dependencies.add(dep)
        return dep
    }



}
