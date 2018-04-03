package com.betomorrow.gradle.library.tasks

import com.betomorrow.gradle.library.context.PluginContext
import com.betomorrow.xamarin.assemblyInfo.AssemblyInfoUpdater
import com.betomorrow.xamarin.assemblyInfo.DefaultAssemblyInfoUpdater
import com.betomorrow.xamarin.descriptors.solution.SolutionDescriptor
import com.betomorrow.xamarin.descriptors.solution.SolutionLoader
import com.betomorrow.xamarin.tools.xbuild.XBuild
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction

import java.nio.file.Files

class BuildTask extends DefaultTask {

    protected XBuild xBuild = PluginContext.current.xbuild

    protected SolutionLoader loader = new SolutionLoader()
    protected AssemblyInfoUpdater assemblyInfoUpdater = new DefaultAssemblyInfoUpdater()

    @Input @Optional
    String solutionFile

    @Input @Optional
    String configuration

    @Input @Optional
    String version

    @TaskAction
     void build() {

        SolutionDescriptor sd = loader.load(project.file(solutionFile))
        sd.projects.each {
            if (Files.exists(it.assemblyInfoPath)) {
                assemblyInfoUpdater.from(it.assemblyInfoPath).withVersion(version).withFileVersion(version).update()
            }
        }

        int result = xBuild.buildCrossLibrary(configuration, solutionFile)
        if (result > 0) {
            throw new GradleException("Can't build library")
        }
    }

}
