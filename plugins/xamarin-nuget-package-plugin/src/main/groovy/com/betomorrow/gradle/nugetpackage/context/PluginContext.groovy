package com.betomorrow.gradle.nugetpackage.context

import com.betomorrow.msbuild.tools.commands.FakeCommandRunner
import com.betomorrow.msbuild.tools.commands.SystemCommandRunner
import com.betomorrow.msbuild.tools.nuget.DefaultNuget
import com.betomorrow.msbuild.tools.nuget.Nuget
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
            instance = [getNuget : { new DefaultNuget(new FakeCommandRunner())}] as ApplicationContext
        } else {
            instance =  [getNuget : { new DefaultNuget(new SystemCommandRunner())}] as ApplicationContext
        }
    }

    interface ApplicationContext {
        Nuget getNuget()
    }

}
