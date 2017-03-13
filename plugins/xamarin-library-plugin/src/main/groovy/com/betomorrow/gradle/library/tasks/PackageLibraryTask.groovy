package com.betomorrow.gradle.library.tasks

import com.betomorrow.gradle.library.context.PluginContext
import com.betomorrow.msbuild.tools.files.FileCopier
import com.betomorrow.msbuild.tools.nuget.Nuget
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class PackageLibraryTask extends DefaultTask {

    protected Nuget nuget = PluginContext.current.nuget

    String nuspecPath
    String suffix
    String outputDirectory

    @TaskAction
    void packageLibrary() {
        nuget.pack(nuspecPath, suffix, outputDirectory)
    }

}
