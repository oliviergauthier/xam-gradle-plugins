package com.betomorrow.gradle.nugetpackage.tasks

import com.betomorrow.msbuild.tools.nuget.DefaultNuget
import com.betomorrow.msbuild.tools.nuget.Nuget
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class PushPackageTask extends DefaultTask {

    protected Nuget nuget = new DefaultNuget()

    String packagePath
    String source
    String apiKey

    @TaskAction
    void pushPackage() {
        def repository = source ?: "${System.getProperty('user.home')}/.nuget"
        nuget.push(packagePath, repository, apiKey)
    }

}
