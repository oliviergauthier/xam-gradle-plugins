package com.betomorrow.gradle.library.tasks

import com.betomorrow.gradle.library.context.PluginContext
import com.betomorrow.xamarin.tools.nuget.Nuget
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction

class PushPackageTask extends DefaultTask {

    protected Nuget nuget = PluginContext.current.nuget

    String packagePath
    String source
    String apiKey

    @TaskAction
    void pushPackage() {
        def repository = source ?: "${System.getProperty('user.home')}/.nuget"
        def result = nuget.push(packagePath, repository, apiKey)
        if (result > 0) {
            throw new GradleException("Can't push package")
        }
    }

}
