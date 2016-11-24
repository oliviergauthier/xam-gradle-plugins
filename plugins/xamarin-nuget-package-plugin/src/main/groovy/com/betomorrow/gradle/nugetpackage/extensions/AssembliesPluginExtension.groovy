package com.betomorrow.gradle.nugetpackage.extensions

import com.betomorrow.msbuild.tools.nuspec.dependencies.Dependency
import org.gradle.api.Project

/**
 * Created by olivier on 23/02/16.
 */
class AssembliesPluginExtension {
    private Project project

    List<Dependency> assemblies = []

    AssembliesPluginExtension(Project project) {
        this.project = project
    }

    AssemblyTarget target(Closure closure) {
        def assembly = project.configure(new AssemblyTarget(), closure)
        assemblies.add(assembly)
        return assembly
    }

}
