package com.betomorrow.gradle.nunit.context

import com.betomorrow.gradle.commons.context.Context
import com.betomorrow.msbuild.tools.commands.FakeCommandRunner
import com.betomorrow.msbuild.tools.commands.SystemCommandRunner
import com.betomorrow.msbuild.tools.nunit.DefaultNunitConsole
import com.betomorrow.msbuild.tools.nunit.NUnitConsole

class PluginContext extends Context<ApplicationContext>{

    static {
        dryRunContext = [getNunitConsole : { new DefaultNunitConsole(new FakeCommandRunner())}] as ApplicationContext

        defaultContext = [getNunitConsole : { new DefaultNunitConsole(new SystemCommandRunner())}] as ApplicationContext
    }

    interface ApplicationContext {
        NUnitConsole getNunitConsole()
    }

}
