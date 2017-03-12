package com.betomorrow.gradle.library.extensions.nuspec

import org.gradle.api.Project

class AssembliesPluginExtension {
    private Project project

    List<AssemblyTarget> assemblies = []

    AssembliesPluginExtension(Project project) {
        this.project = project
    }

    AssemblyTarget target(Closure closure) {
        def assembly = project.configure(new AssemblyTarget(), closure)
        assemblies.add(assembly)
        return assembly
    }

}
