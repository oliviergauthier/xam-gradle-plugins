package com.betomorrow.gradle.nunit.context

import com.betomorrow.msbuild.tools.commands.FakeCommandRunner
import com.betomorrow.msbuild.tools.commands.SystemCommandRunner
import com.betomorrow.msbuild.tools.nunit.DefaultNunitConsole
import com.betomorrow.msbuild.tools.nunit.NUnitConsole
import org.gradle.api.Project

class PluginContext {

    private static ApplicationContext instance

    static ApplicationContext getCurrent() {
        return instance
    }

    static void configure(Project project) {
        configure(project.hasProperty("dryRun") && project.dryRun)
    }

    static void configure(boolean dryRun) {
        if (dryRun) {
            instance = [getNunitConsole : { new DefaultNunitConsole(new FakeCommandRunner())}] as ApplicationContext
        } else {
            instance =  [getNunitConsole : { new DefaultNunitConsole(new SystemCommandRunner())}] as ApplicationContext
        }
    }

    interface ApplicationContext {
        NUnitConsole getNunitConsole()
    }

}
