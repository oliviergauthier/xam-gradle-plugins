package com.betomorrow.msbuild.tools.nunit

import com.betomorrow.msbuild.tools.commands.CommandRunner
import com.betomorrow.msbuild.tools.commands.SystemCommandRunner

import java.nio.file.Paths

class DefaultNunitConsole implements NUnitConsole {

    protected NUnitConsole3Downloader downloader = new NUnitConsole3Downloader()
    protected CommandRunner runner = new SystemCommandRunner()

    DefaultNunitConsole(CommandRunner runner) {
        this.runner = runner
    }

    @Override
    void run(List<String> assemblies, String format, String version) {
        def nunitConsolePath = getNunitConsolePath(version)
        runner.run(new NunitRunCmd(nunitConsolePath, assemblies, format))
    }

    @Override
    void run(List<String> assemblies, String format) {
        run(assemblies, format, "3.6.0")
    }

    String getNunitConsolePath(String version) {
        return Paths.get(downloader.download(version)).resolve("nunit3-console.exe").toString()
    }

}
