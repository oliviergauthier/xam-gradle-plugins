package com.betomorrow.xamarin.tools.nuget

import com.betomorrow.xamarin.commands.CommandRunner
import com.betomorrow.xamarin.commands.DefaultCommandRunner
import com.betomorrow.xamarin.files.DefaultFileCopier
import com.betomorrow.xamarin.files.FileCopier

import java.nio.file.Files
import java.nio.file.Paths

class NugetBuilder {

    static final String DEFAULT_NUGET_VERSION = "4.5.1"

    private FileCopier filesCopier = new DefaultFileCopier()

    CommandRunner runner
    String nugetVersion
    String nugetPath

    NugetBuilder withCommandRunner(CommandRunner runner) {
        this.runner = runner
        return this
    }

    NugetBuilder withVersion(String nugetVersion) {
        this.nugetVersion = nugetVersion
        return this
    }

    NugetBuilder withNugetPath(String nugetPath) {
        this.nugetPath = nugetPath
        return this
    }

    Nuget build() {
        if (runner == null) {
            runner = DefaultCommandRunner.INSTANCE
        }

        if (nugetPath) {
            return new DefaultNuget(runner, nugetPath)
        }

        if (nugetVersion == null) {
            nugetVersion = DEFAULT_NUGET_VERSION
        }

        nugetPath = resolveNugetPath(nugetVersion)

        if (!Files.exists(Paths.get(nugetPath))) {
            downloadNuget()
        }

        return new DefaultNuget(runner, nugetPath)
    }

    private static String resolveNugetPath(String nugetVersion) {
        return Paths.get(System.getProperty("user.home"))
                .resolve(".nuget")
                .resolve("caches")
                .resolve("nuget-${nugetVersion}.exe")
                .toAbsolutePath().toString()
    }

    private void downloadNuget() {
        def url = "https://dist.nuget.org/win-x86-commandline/v${nugetVersion}/nuget.exe"
        filesCopier.download(new URL(url), Paths.get(nugetPath))
    }

}
