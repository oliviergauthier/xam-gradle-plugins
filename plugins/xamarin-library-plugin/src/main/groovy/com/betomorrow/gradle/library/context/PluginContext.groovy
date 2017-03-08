package com.betomorrow.gradle.library.context

import com.betomorrow.msbuild.tools.commands.FakeCommandRunner
import com.betomorrow.msbuild.tools.commands.SystemCommandRunner
import com.betomorrow.msbuild.tools.nuget.DefaultNuget
import com.betomorrow.msbuild.tools.nuget.Nuget
import com.betomorrow.xamarin.xbuild.XBuild
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
            instance = [getXbuild : { new XBuild(new FakeCommandRunner())},
                        getNuget : { new DefaultNuget(new FakeCommandRunner())}] as ApplicationContext
        } else {
            instance =  [getXbuild : { new XBuild(new SystemCommandRunner())},
                         getNuget : { new DefaultNuget(new SystemCommandRunner())}] as ApplicationContext
        }
    }

    interface ApplicationContext {
        XBuild getXbuild()
        Nuget getNuget()
    }

}
