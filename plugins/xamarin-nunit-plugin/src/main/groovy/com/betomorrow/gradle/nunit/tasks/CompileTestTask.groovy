package com.betomorrow.gradle.nunit.tasks

import com.betomorrow.gradle.nunit.context.PluginContext
import com.betomorrow.xamarin.descriptors.solution.SolutionLoader
import com.betomorrow.xamarin.tools.xbuild.XBuild
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction

class CompileTestTask extends DefaultTask {

    protected XBuild xbuild = PluginContext.current.xbuild
    protected SolutionLoader loader = new SolutionLoader()

    List<String> projects

    @TaskAction
    void run() {
        if (projects) {
            def sd = loader.load(project.file(project.solution))
            projects.each {
                def result = xbuild.buildSingleProject(project.configuration, sd.getProject(it).getPath().toString())
                if (result > 0) {
                    throw new GradleException("Can't compile tests")
                }
            }
        }
    }

}
