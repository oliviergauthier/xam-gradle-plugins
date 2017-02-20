package com.betomorrow.gradle.nugetpackage.context

import com.betomorrow.gradle.commons.context.Context
import com.betomorrow.msbuild.tools.commands.FakeCommandRunner
import com.betomorrow.msbuild.tools.commands.SystemCommandRunner
import com.betomorrow.msbuild.tools.nuget.DefaultNuget
import com.betomorrow.msbuild.tools.nuget.Nuget

class PluginContext extends Context<ApplicationContext> {

    static {
        dryRunContext = [getNuget : { new DefaultNuget(new FakeCommandRunner())}] as ApplicationContext

        defaultContext = [getNuget : { new DefaultNuget(new SystemCommandRunner())}] as ApplicationContext
    }

    interface ApplicationContext {
        Nuget getNuget()
    }

}
