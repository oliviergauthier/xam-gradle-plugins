package com.betomorrow.xamarin.tools.nuget

import com.betomorrow.xamarin.commands.SystemCommandRunner
import com.betomorrow.xamarin.commands.CommandRunner
import com.betomorrow.xamarin.files.DefaultFileCopier
import com.betomorrow.xamarin.files.FileCopier

import java.nio.file.Paths

class DefaultNuget implements Nuget {

    public static final String DEFAULT_NUGET_VERSION = "4.5.1"

    private CommandRunner runner = new SystemCommandRunner()
    private FileCopier filesCopier = new DefaultFileCopier()

    private String nugetVersion
    private String configuredNugetPath

    DefaultNuget(CommandRunner runner) {
        this.runner = runner
        this.nugetVersion = DEFAULT_NUGET_VERSION
    }


    DefaultNuget(CommandRunner runner, String nugetVersion, String nugetPath) {
        this.runner = runner
        this.nugetVersion = nugetVersion
        this.configuredNugetPath = nugetPath
    }

    String getNugetPath() {
        if (configuredNugetPath) {
            return configuredNugetPath
        }

        return Paths.get(System.getProperty("user.home"))
                .resolve(".nuget")
                .resolve("caches")
                .resolve("nuget-${nugetVersion}.exe")
                .toAbsolutePath().toString()
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

    private void downloadNuget() {
        def url = "https://dist.nuget.org/win-x86-commandline/v${nugetVersion}/nuget.exe"
        filesCopier.download(new URL(url), Paths.get(nugetPath))
    }

    private int execute(CommandRunner.Cmd cmd) {
        if (!new File(nugetPath).exists()) {
            if (configuredNugetPath != nugetPath) {
                downloadNuget()
            } else {
                throw new Exception("Can't find nuget at ${configuredNugetPath}")
            }
        }

        return runner.run(cmd)
    }


}
