package com.betomorrow.gradle.library.tasks

import com.betomorrow.gradle.library.context.PluginContext
import com.betomorrow.xamarin.assemblyInfo.AssemblyInfoUpdater
import com.betomorrow.xamarin.assemblyInfo.DefaultAssemblyInfoUpdater
import com.betomorrow.xamarin.descriptors.solution.SolutionDescriptor
import com.betomorrow.xamarin.descriptors.solution.SolutionLoader
import com.betomorrow.xamarin.xbuild.XBuild
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class BuildTask extends DefaultTask {

    protected XBuild xBuild = PluginContext.current.xbuild
    protected SolutionLoader loader = new SolutionLoader()
    protected AssemblyInfoUpdater assemblyInfoUpdater = new DefaultAssemblyInfoUpdater()

    String solutionFile
    String configuration
    String version

    @TaskAction
     void build() {

        SolutionDescriptor sd = loader.load(project.file(solutionFile))
        sd.projects.each {
            assemblyInfoUpdater.from(it.assemblyInfoPath).withVersion("1.0").update()
        }

        xBuild.buildCrossLibrary(configuration, solutionFile)
    }

}
