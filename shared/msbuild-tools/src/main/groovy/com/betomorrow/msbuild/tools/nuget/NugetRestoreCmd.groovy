package com.betomorrow.msbuild.tools.nuget

import com.betomorrow.msbuild.tools.commands.CommandRunner

class NugetRestoreCmd implements CommandRunner.Cmd {

    String nugetPath = 'nuget'

    @Override
    List<String> build() {
        return [nugetPath, 'restore']
    }
}
