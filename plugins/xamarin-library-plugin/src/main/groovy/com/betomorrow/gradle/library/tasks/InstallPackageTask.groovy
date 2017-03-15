package com.betomorrow.gradle.library.tasks

import com.betomorrow.gradle.library.context.PluginContext

import com.betomorrow.msbuild.tools.nuget.Nuget
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction

class InstallPackageTask extends DefaultTask {

    protected Nuget nuget = PluginContext.current.nuget

    String packagePath
    String source
    String format

    @TaskAction
    void installPackage() {
        switch (format) {
            case PublishLocalPluginExtension.NUGET_3:
                def repository = source ?: "${System.getProperty('user.home')}${File.separator}.nuget${File.separator}packages"
                nuget.install(packagePath, repository)
                break
            case PublishLocalPluginExtension.NUGET_2:
                def repository = source ?: "${System.getProperty('user.home')}${File.separator}.nuget"
                nuget.push(packagePath, repository, null)
                break
            default:
                throw new GradleException("Invalid format for local repository")
        }
    }

}
