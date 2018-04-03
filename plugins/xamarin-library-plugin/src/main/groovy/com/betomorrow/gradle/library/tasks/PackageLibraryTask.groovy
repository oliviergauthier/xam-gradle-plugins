package com.betomorrow.gradle.library.tasks

import com.betomorrow.gradle.library.context.PluginContext
import com.betomorrow.xamarin.tools.nuget.Nuget
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

class PackageLibraryTask extends DefaultTask {

    protected Nuget nuget = PluginContext.current.nuget

    @Input @Optional
    String nuspecPath

    @Input @Optional
    String suffix

    @OutputDirectory
    String outputDirectory

    @TaskAction
    void packageLibrary() {
        def result = nuget.pack(nuspecPath, suffix, outputDirectory)
        if (result > 0) {
            throw new GradleException("Can't package library")
        }
    }

}
