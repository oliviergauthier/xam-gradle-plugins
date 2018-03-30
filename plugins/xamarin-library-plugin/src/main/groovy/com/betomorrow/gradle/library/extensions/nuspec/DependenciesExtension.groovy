package com.betomorrow.gradle.library.extensions.nuspec

import com.betomorrow.xamarin.tools.nuspec.dependencies.Dependency

class DependenciesExtension {

    private String name
    private String baseOn

    List<Dependency> dependencies = []

    DependenciesExtension(String name) {
        this.name = name
    }

    Dependency dependency(String dependency) {
        def dep = new Dependency(dependency)
        dependencies.add(dep)
        return dep
    }

    String baseOn(String baseOn) {
        this.baseOn = baseOn
    }

    List<Dependency> getDependencies(NuspecItemExtension nuspecItem) {
        if (name == "default") {
            return dependencies
        }

        HashSet<Dependency> result = []

        if (baseOn) {
            nuspecItem.getDependencies(baseOn).forEach {
                result.add(new Dependency(name, it.id, it.version))
            }
        }

        dependencies.forEach {
            result.add(new Dependency(name, it.id, it.version))
        }

        return result.toList()
    }

}
