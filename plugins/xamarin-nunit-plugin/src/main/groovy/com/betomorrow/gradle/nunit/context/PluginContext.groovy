package com.betomorrow.gradle.nunit.context

import com.betomorrow.xamarin.commands.CommandRunner
import com.betomorrow.xamarin.commands.FakeCommandRunner
import com.betomorrow.xamarin.commands.SystemCommandRunner
import com.betomorrow.xamarin.tools.nuget.Nuget
import com.betomorrow.xamarin.tools.nuget.NugetBuilder
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
        boolean  dryRun = project.hasProperty("dryRun") && project.dryRun
        instance = dryRun ? fakeApplicationContext(project) : realApplicationContext(project)
    }

    private static ApplicationContext fakeApplicationContext(Project project) {
        String msBuildPath = project.hasProperty("msbuildPath") ? project.property("msbuildPath") : null
        String nugetPath = project.hasProperty("nugetPath") ? project.property("nugetPath") : null
        String nugetVersion = project.hasProperty("nugetVersion") ? project.property("nugetVersion") : null

        CommandRunner commandRunnerInstance = new FakeCommandRunner()

        NugetBuilder nugetBuilder = new NugetBuilder().withCommandRunner(commandRunnerInstance)
        if (nugetPath) {
            nugetBuilder.withNugetPath(nugetPath)
        } else if (nugetVersion) {
            nugetBuilder.withVersion(nugetVersion)
        }
        Nuget nugetInstance = nugetBuilder.build()
        NUnitConsole nunitConsoleInstance = new DefaultNunitConsole(commandRunnerInstance)
        XBuild msbuildInstance = new XBuild(commandRunnerInstance, msBuildPath)

        return [getNunitConsole : { nunitConsoleInstance },
                getXbuild : { msbuildInstance },
                getNuget : { nugetInstance }] as ApplicationContext
    }

    private static ApplicationContext realApplicationContext(Project project) {
        String msBuildPath = project.hasProperty("msbuildPath") ? project.property("msbuildPath") : null
        String nugetPath = project.hasProperty("nugetPath") ? project.property("nugetPath") : null
        String nugetVersion = project.hasProperty("nugetVersion") ? project.property("nugetVersion") : null

        CommandRunner commandRunnerInstance = new SystemCommandRunner()

        NugetBuilder nugetBuilder = new NugetBuilder().withCommandRunner(commandRunnerInstance)
        if (nugetPath) {
            nugetBuilder.withNugetPath(nugetPath)
        } else if (nugetVersion) {
            nugetBuilder.withVersion(nugetVersion)
        }
        Nuget nugetInstance = nugetBuilder.build()
        NUnitConsole nunitConsoleInstance = new DefaultNunitConsole(commandRunnerInstance)
        XBuild msbuildInstance = new XBuild(commandRunnerInstance, msBuildPath)

        return [getNunitConsole : { nunitConsoleInstance },
                getXbuild : { msbuildInstance },
                getNuget : { nugetInstance },] as ApplicationContext
    }

    private static

    interface ApplicationContext {
        NUnitConsole getNunitConsole()
        XBuild getXbuild()
        Nuget getNuget()
    }

}
