package com.betomorrow.xamarin.tools.nuget

import com.betomorrow.xamarin.commands.CommandRunner
import com.betomorrow.xamarin.commands.SystemCommandRunner

class DefaultNuget implements Nuget {

    private CommandRunner runner = new SystemCommandRunner()

    private String nugetPath

    DefaultNuget(CommandRunner runner, String nugetPath) {
        this.runner = runner
        this.nugetPath = nugetPath
    }

    int install(String packagePath, String source) {
        return execute(new NugetAddCmd(nugetPath, packagePath, source))
    }

    int pack(String packagePath, String suffix, String outputDirectory) {
        return execute(new NugetPackCmd(nugetPath, packagePath, suffix, outputDirectory))
    }

    int push(String packagePath, String source, String apiKey) {
        return execute(new NugetPushCmd(nugetPath, packagePath, source, apiKey))
    }

    int restore() {
        return execute(new NugetRestoreCmd(nugetPath))
    }

    private int execute(CommandRunner.Cmd cmd) {
        if (!new File(nugetPath).exists()) {
            throw new Exception("Can't find nuget at ${nugetPath}")
        }

        return runner.run(cmd)
    }


}
