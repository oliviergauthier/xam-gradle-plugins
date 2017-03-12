package com.betomorrow.msbuild.tools.nuget

import com.betomorrow.msbuild.tools.commands.CommandRunner

class NugetPackCmd implements CommandRunner.Cmd {

    String nugetPath
    String suffix
    String nuspecPath

    NugetPackCmd(String nugetPath, String nuspecPath, String suffix) {
        this.nugetPath = nugetPath
        this.suffix = suffix
        this.nuspecPath = nuspecPath
    }

    @Override
    List<String> build() {
        List<String> cmd = ["mono", nugetPath, 'pack', nuspecPath]

        if (suffix != null && !suffix.isEmpty()) {
            cmd.add("-suffix")
            cmd.add(suffix)
        }

        return cmd
    }
}
