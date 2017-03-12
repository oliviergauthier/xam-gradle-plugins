package com.betomorrow.msbuild.tools.nuget

import com.betomorrow.msbuild.tools.commands.CommandRunner

class NugetRestoreCmd implements CommandRunner.Cmd {

    private String nugetPath

    NugetRestoreCmd(String nugetPath) {
        this.nugetPath = nugetPath
    }

    @Override
    List<String> build() {
        return ["mono", nugetPath, 'restore']
    }
}
