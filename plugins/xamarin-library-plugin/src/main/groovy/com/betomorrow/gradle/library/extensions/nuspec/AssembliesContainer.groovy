package com.betomorrow.gradle.library.extensions.nuspec

import org.gradle.api.Project

class AssembliesContainer {
    private Project project

    List<AssemblyTarget> assemblies = []

    AssembliesContainer(Project project) {
        this.project = project
    }

    AssemblyTarget target(Closure closure) {
        def assembly = project.configure(new AssemblyTarget(), closure)
        assemblies.add(assembly)
        return assembly
    }

}
