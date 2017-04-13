package com.betomorrow.gradle.application.tasks

import com.betomorrow.gradle.application.context.PluginContext
import com.betomorrow.xamarin.tools.nuget.Nuget
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction

class NugetRestoreTask extends DefaultTask {

    protected Nuget nuget = PluginContext.current.nuget

    @TaskAction
    void restore() {
        def result = nuget.restore()
        if (result > 0) {
            throw new GradleException("Can't restore package")
        }
    }

}