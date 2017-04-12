package com.betomorrow.gradle.nunit.tasks

import com.betomorrow.gradle.nunit.context.PluginContext
import com.betomorrow.xamarin.tools.nuget.Nuget
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class NugetRestoreTask extends DefaultTask {

    protected Nuget nuget = PluginContext.current.nuget

    @TaskAction
    void restore() {
        nuget.restore()
    }

}