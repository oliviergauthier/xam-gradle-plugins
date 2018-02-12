package com.betomorrow.gradle.library.extensions.nuspec

import com.betomorrow.xamarin.tools.nuspec.dependencies.Dependency

class DependenciesExtension {

    private String name

    List<Dependency> dependencies = []

    DependenciesExtension(String name) {
        this.name = name
    }

    Dependency dependency(String dependency) {
        def dep = new Dependency(dependency)
        dependencies.add(dep)
        return dep
    }

}
