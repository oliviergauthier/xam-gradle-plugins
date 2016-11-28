package com.betomorrow.gradle.library.tasks

import com.betomorrow.xamarin.descriptors.solution.SolutionDescriptor
import com.betomorrow.xamarin.descriptors.solution.SolutionLoader
import com.betomorrow.xamarin.xbuild.XBuild
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class BuildTask extends DefaultTask {

    protected XBuild xBuild = new XBuild()
    protected SolutionLoader solutionLoader = new SolutionLoader()

    String solution
    String configuration

    @TaskAction
     void build() {
        SolutionDescriptor sd = solutionLoader.load(solution)

        sd.getProjects(configuration).forEach {
            xBuild.build(configuration, it.path)
        }

    }

}
