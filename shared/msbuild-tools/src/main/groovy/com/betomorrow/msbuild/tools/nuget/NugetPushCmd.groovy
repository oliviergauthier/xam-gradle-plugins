package com.betomorrow.msbuild.tools.nuget

import com.betomorrow.msbuild.tools.commands.CommandRunner

class NugetPushCmd implements CommandRunner.Cmd {

    String nugetPath
    String packagePath
    String apiKey
    String source

    NugetPushCmd(String nugetPath, String packagePath, String apiKey, String source) {
        this.nugetPath = nugetPath
        this.packagePath = packagePath
        this.apiKey = apiKey
        this.source = source
    }

    @Override
    List<String> build() {
        List<String> cmd = ["mono", nugetPath, 'push', packagePath]

        if (source) {
            cmd.addAll(["-source", source])
        }

        if (apiKey) {
            cmd.addAll(["-apikey", apiKey])
        }

        return cmd
    }
}
