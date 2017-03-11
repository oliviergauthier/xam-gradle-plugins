package com.betomorrow.gradle.nugetpackage.tasks

import com.betomorrow.gradle.nugetpackage.context.PluginContext
import com.betomorrow.msbuild.tools.files.FileCopier
import com.betomorrow.msbuild.tools.nuget.Nuget
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class PackageLibraryTask extends DefaultTask {

    protected Nuget nuget = PluginContext.current.nuget
    protected FileCopier fileCopier = PluginContext.current.fileCopier

    String nuspecPath
    String suffix
    String output
    String packageName

    @TaskAction
    void packageLibrary() {
        nuget.pack(nuspecPath, suffix)

        fileCopier.move(project.file(packageName).toPath(), project.file(output).toPath())
    }

}
