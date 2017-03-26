package com.betomorrow.xamarin.tools.nuget

import com.betomorrow.xamarin.commands.CommandRunner

class NugetPackCmd implements CommandRunner.Cmd {

    String nugetPath
    String suffix
    String nuspecPath
    String outputDirectory

    NugetPackCmd(String nugetPath, String nuspecPath, String suffix, String outputDirectory) {
        this.nugetPath = nugetPath
        this.suffix = suffix
        this.nuspecPath = nuspecPath
        this.outputDirectory = outputDirectory
    }

    @Override
    List<String> build() {
        List<String> cmd = ["mono", nugetPath, 'pack', nuspecPath]

        if (suffix) {
            cmd.add("-suffix")
            cmd.add(suffix)
        }

        if (outputDirectory) {
            cmd.add("-outputdirectory")
            cmd.add(outputDirectory)
        }

        return cmd
    }
}
