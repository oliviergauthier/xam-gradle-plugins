package com.betomorrow.xamarin.tools.nunit

import com.betomorrow.xamarin.commands.SystemCommandRunner
import com.betomorrow.xamarin.commands.CommandRunner

import java.nio.file.Paths

class DefaultNunitConsole implements NUnitConsole {

    protected NUnitConsole3Downloader downloader = new NUnitConsole3Downloader()
    protected CommandRunner runner = new SystemCommandRunner()

    DefaultNunitConsole(CommandRunner runner) {
        this.runner = runner
    }

    @Override
    int run(List<String> assemblies, String format, String version) {
        def nunitConsolePath = getNunitConsolePath(version)
        return runner.run(new NunitRunCmd(nunitConsolePath, assemblies, format))
    }

    @Override
    int run(List<String> assemblies, String format) {
        return run(assemblies, format, "3.6.0")
    }

    String getNunitConsolePath(String version) {
        return Paths.get(downloader.download(version)).resolve("nunit3-console.exe").toString()
    }

}
