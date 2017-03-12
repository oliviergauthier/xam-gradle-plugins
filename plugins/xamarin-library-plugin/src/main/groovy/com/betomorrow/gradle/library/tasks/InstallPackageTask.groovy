package com.betomorrow.gradle.library.tasks

import com.betomorrow.gradle.library.context.PluginContext
import com.betomorrow.msbuild.tools.nuget.Nuget
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class InstallPackageTask extends DefaultTask {

    protected Nuget nuget = PluginContext.current.nuget

    String packagePath
    String source
    String format

    @TaskAction
    void installPackage() {
        if (format == "nuget3") {
            def repository = source ?: "${System.getProperty('user.home')}${File.separator}.nuget${File.separator}packages"
            nuget.install(packagePath, repository)
        } else {
            def repository = source ?: "${System.getProperty('user.home')}${File.separator}.nuget"
            nuget.push(packagePath, repository, null)
        }
    }

}
