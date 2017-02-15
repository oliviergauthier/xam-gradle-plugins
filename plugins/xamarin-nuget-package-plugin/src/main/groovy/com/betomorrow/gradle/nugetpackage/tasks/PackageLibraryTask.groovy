package com.betomorrow.gradle.nugetpackage.tasks

import com.betomorrow.msbuild.tools.nuget.DefaultNuget
import com.betomorrow.msbuild.tools.nuget.Nuget
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class PackageLibraryTask extends DefaultTask {

    protected Nuget nuget = new DefaultNuget()

    String nuspecPath
    String suffix
    String output

    @TaskAction
    void packageLibrary() {
        nuget.pack(nuspecPath, suffix)
    }

}
