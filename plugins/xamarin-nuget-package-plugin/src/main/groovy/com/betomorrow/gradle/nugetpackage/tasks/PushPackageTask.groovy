package com.betomorrow.gradle.nugetpackage.tasks

import com.betomorrow.gradle.nugetpackage.context.PluginContext
import com.betomorrow.msbuild.tools.nuget.Nuget
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class PushPackageTask extends DefaultTask {

    protected Nuget nuget = PluginContext.current.nuget

    String packagePath
    String source
    String apiKey

    @TaskAction
    void pushPackage() {
        def repository = source ?: "${System.getProperty('user.home')}/.nuget"
        nuget.push(packagePath, repository, apiKey)
    }

}
