package com.betomorrow.gradle.library.tasks

import com.betomorrow.gradle.library.extensions.nuspec.AssemblyTarget
import com.betomorrow.xamarin.tools.nuspec.NuSpec
import com.betomorrow.xamarin.tools.nuspec.NuSpecWriter
import com.betomorrow.xamarin.tools.nuspec.XmlNuSpecWriter
import com.betomorrow.xamarin.tools.nuspec.assemblies.Assembly
import com.betomorrow.xamarin.tools.nuspec.dependencies.Dependency
import com.betomorrow.xamarin.tools.nuspec.dependencies.DependencySet
import com.betomorrow.xamarin.descriptors.project.XamarinProjectDescriptor
import com.betomorrow.xamarin.descriptors.solution.SolutionDescriptor
import com.betomorrow.xamarin.descriptors.solution.SolutionLoader
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class GenerateNuspecTask extends DefaultTask {

    protected NuSpecWriter writer = new XmlNuSpecWriter()
    protected SolutionLoader loader = new SolutionLoader()

    String output

    String title
    String packageId
    String version
    String authors
    String owners
    String licenseUrl
    String projectUrl
    String iconUrl
    Boolean requireLicenseAcceptance
    String description
    String releaseNotes
    String copyright
    String tags

    List<Dependency> dependencies
    List<AssemblyTarget> assemblies

    @TaskAction
     void generateNuspec() {
        NuSpec nuSpec = new NuSpec()

        nuSpec.output = output

        nuSpec.title = title
        nuSpec.packageId = packageId
        nuSpec.version = version
        nuSpec.authors = authors
        nuSpec.owners = owners
        nuSpec.licenseUrl = licenseUrl
        nuSpec.projectUrl = projectUrl
        nuSpec.iconUrl = iconUrl
        nuSpec.requireLicenseAcceptance = requireLicenseAcceptance
        nuSpec.description = description
        nuSpec.releaseNotes = releaseNotes
        nuSpec.copyright = copyright
        nuSpec.tags = tags
        nuSpec.dependencySet = new DependencySet(dependencies)

        def solution = loader.load(project.file(project.solution))

        assemblies.each { target ->
            target.includes.each {
                resolveAssembly(solution, it).forEach { assembly ->
                    nuSpec.assemblySet.addAll(new Assembly(assembly, target.dest))
                }
            }
        }

        writer.write(nuSpec)
    }

    List<String> resolveAssembly(SolutionDescriptor solution, String name) {
        XamarinProjectDescriptor pd = solution.getProject(name)
        if (pd == null) {
            return [name]
        }

        def assemblies = []
        assemblies.add(project.file(".").toPath().relativize(pd.getLibraryOutputPath("Release")).toString())
        if (pd.hasDebugSymbols("Release")) {
            assemblies.add(project.file(".").toPath().relativize(pd.getSymbolsOutputPath("Release")).toString())
        }

        return assemblies
    }

}
