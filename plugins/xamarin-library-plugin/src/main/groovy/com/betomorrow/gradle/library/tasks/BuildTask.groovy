package com.betomorrow.gradle.library.tasks

import com.betomorrow.msbuild.tools.descriptors.solution.SolutionDescriptor
import com.betomorrow.msbuild.tools.descriptors.solution.SolutionLoader
import com.betomorrow.msbuild.tools.xbuild.XBuild
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class BuildTask extends DefaultTask {

    protected XBuild xBuild = new XBuild();
    protected SolutionLoader solutionLoader = new SolutionLoader();

    String solution;
    String configuration;

    @TaskAction
    public void build() {
        SolutionDescriptor sd = solutionLoader.load(solution);

        sd.getProjects(configuration).forEach {
            xBuild.build(configuration, it.path);
        }

    }

}
