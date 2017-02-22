package com.betomorrow.msbuild.tools.nunit

import com.betomorrow.msbuild.tools.commands.CommandRunner
import com.betomorrow.msbuild.tools.commands.SystemCommandRunner

class DefaultNunitConsole implements NUnitConsole {

    protected CommandRunner runner = new SystemCommandRunner()

    @Override
    void run(List<String> assemblies, String format) {
        runner.run(new NunitRunCmd(assemblies, format))
    }

}
