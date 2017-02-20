package com.betomorrow.msbuild.tools.nuget

import com.betomorrow.msbuild.tools.commands.CommandRunner

class NugetPackCmd implements CommandRunner.Cmd {

    String nugetPath = 'nuget'

    String suffix
    String nuspecPath

    @Override
    List<String> build() {
        List<String> cmd = [nugetPath, 'pack']

        if (suffix) {
            cmd.add("-suffix ${suffix}")
        }

        return cmd
    }
}
