package com.betomorrow.xamarin.tools.nuget

import com.betomorrow.xamarin.commands.SystemCommandRunner
import com.betomorrow.xamarin.commands.CommandRunner
import com.betomorrow.xamarin.files.DefaultFileCopier
import com.betomorrow.xamarin.files.FileCopier

import java.nio.file.Paths

class DefaultNuget implements Nuget {

    private CommandRunner runner = new SystemCommandRunner()
    private FileCopier filesCopier = new DefaultFileCopier()
    private String nugetVersion = "3.5.0"

    DefaultNuget(CommandRunner runner) {
        this.runner = runner
    }

    String getNugetPath() {
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
            downloadNuget()
        }

        return runner.run(cmd)
    }


}
