package com.betomorrow.gradle.library.tasks

import com.betomorrow.gradle.library.context.PluginContext
import com.betomorrow.xamarin.xbuild.XBuild
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class BuildTask extends DefaultTask {

    protected XBuild xBuild = PluginContext.current.xbuild

    String solutionFile
    String configuration

    @TaskAction
     void build() {
        xBuild.buildCrossLibrary(configuration, solutionFile)
    }

}
