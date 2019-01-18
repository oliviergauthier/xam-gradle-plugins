package com.betomorrow.gradle.library.tasks

import com.betomorrow.gradle.library.extensions.nuspec.AssemblyTarget
import com.betomorrow.xamarin.descriptors.project.SymbolsFormat
import com.betomorrow.xamarin.descriptors.project.XamarinProjectDescriptor
import com.betomorrow.xamarin.descriptors.solution.SolutionDescriptor
import com.betomorrow.xamarin.descriptors.solution.SolutionLoader
import com.betomorrow.xamarin.tools.nuspec.NuSpec
import com.betomorrow.xamarin.tools.nuspec.NuSpecWriter
import com.betomorrow.xamarin.tools.nuspec.XmlNuSpecWriter
import com.betomorrow.xamarin.tools.nuspec.assemblies.Assembly
import com.betomorrow.xamarin.tools.nuspec.dependencies.Dependency
import com.betomorrow.xamarin.tools.nuspec.dependencies.DependencySet
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

import java.nio.file.Files
import java.nio.file.Path

class GenerateNuspecTask extends DefaultTask {

    protected NuSpecWriter writer = new XmlNuSpecWriter()
    protected SolutionLoader loader = new SolutionLoader()

    @OutputFile
    File output

    @Input @Optional
    String title

    @Input @Optional
    String packageId

    @Input @Optional
    String version

    @Input @Optional
    String authors

    @Input @Optional
    String owners

    @Input @Optional
    String licenseUrl

    @Input @Optional
    String projectUrl

    @Input @Optional
    String iconUrl

    @Input @Optional
    Boolean requireLicenseAcceptance

    @Input @Optional
    String description

    @Input @Optional
    String releaseNotes

    @Input @Optional
    String copyright

    @Input @Optional
    String tags

    @Input @Optional
    String configuration

    List<Dependency> dependencies

    List<AssemblyTarget> assemblies

    @Input
    String checkDependencies() {
        return dependencies.collect { it -> "${it.group}:${it.id}:${it.version}" }.join(",")
    }

    @Input
    String checkAssemblies() {
        return assemblies.collect { it -> "${it.dest}:${it.includes}" }.join(",")
    }

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
                resolveAssembly(solution, target.target, it).forEach { assembly ->
                    nuSpec.assemblySet.addAll(new Assembly(assembly, target.dest))
                }
            }
        }

        writer.write(nuSpec)
    }

    List<String> resolveAssembly(SolutionDescriptor solution, String target, String name) {
        XamarinProjectDescriptor pd = solution.getProject(name)
        if (pd == null) {
            return [name]
        }

        def assemblies = []
        assemblies.add(project.file(".").toPath().relativize(pd.getLibraryOutputPath(configuration, target)).toString())

        def debugSymbolsPath = getSymbolsPath(pd, target)
        if (debugSymbolsPath != null) {
            assemblies.add(project.projectDir.toPath().relativize(debugSymbolsPath).toString())
        }
        return assemblies
    }

    private Path getSymbolsPath(XamarinProjectDescriptor pd, String target) {
        if (!pd.hasDebugSymbols(configuration)) {
            return null
        }

        def path = pd.getSymbolsOutputPath(configuration, target)
        if (Files.exists(path)) {
            return path
        }

        path = pd.getSymbolsOutputPath(configuration, target, SymbolsFormat.PDB)
        if (Files.exists(path)) {
            return path
        }

        return null
    }

}
