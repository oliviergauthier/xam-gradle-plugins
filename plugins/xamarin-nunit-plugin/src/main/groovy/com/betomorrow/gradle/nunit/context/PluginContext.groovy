package com.betomorrow.gradle.nunit.context

import com.betomorrow.xamarin.commands.FakeCommandRunner
import com.betomorrow.xamarin.commands.SystemCommandRunner
import com.betomorrow.xamarin.tools.nunit.DefaultNunitConsole
import com.betomorrow.xamarin.tools.nunit.NUnitConsole
import com.betomorrow.xamarin.tools.xbuild.XBuild
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
            instance = [getNunitConsole : { new DefaultNunitConsole(new FakeCommandRunner())},
                        getXbuild : { new XBuild(new FakeCommandRunner())}] as ApplicationContext
        } else {
            instance =  [getNunitConsole : { new DefaultNunitConsole(new SystemCommandRunner())},
                         getXbuild : { new XBuild(new SystemCommandRunner())}] as ApplicationContext
        }
    }

    interface ApplicationContext {
        NUnitConsole getNunitConsole()
        XBuild getXbuild()
    }

}
