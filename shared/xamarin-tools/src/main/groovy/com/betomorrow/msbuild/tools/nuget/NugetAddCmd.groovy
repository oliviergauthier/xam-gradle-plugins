package com.betomorrow.msbuild.tools.nuget

import com.betomorrow.msbuild.tools.commands.CommandRunner

class NugetAddCmd implements CommandRunner.Cmd {

    private String nugetPath
    private String packagePath
    private String source

    NugetAddCmd(String nugetPath, String packagePath, String source) {
        this.nugetPath = nugetPath
        this.packagePath = packagePath
        this.source = source
    }

    @Override
    List<String> build() {
        return ["mono", nugetPath, "add", packagePath, "-source", source]
    }
}
