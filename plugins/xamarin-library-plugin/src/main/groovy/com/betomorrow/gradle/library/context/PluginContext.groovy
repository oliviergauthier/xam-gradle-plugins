package com.betomorrow.gradle.library.context

import com.betomorrow.xamarin.commands.FakeCommandRunner
import com.betomorrow.xamarin.commands.SystemCommandRunner
import com.betomorrow.xamarin.files.DefaultFileCopier
import com.betomorrow.xamarin.files.FakeFileCopier
import com.betomorrow.xamarin.files.FileCopier
import com.betomorrow.xamarin.tools.nuget.DefaultNuget
import com.betomorrow.xamarin.tools.nuget.Nuget
import com.betomorrow.xamarin.tools.xbuild.XBuild
import org.gradle.api.Project

class PluginContext {

    private static ApplicationContext instance

    static ApplicationContext getCurrent() {
        return instance
    }

    static void configure(Project project) {
        boolean  dryRun = project.hasProperty("dryRun") && project.dryRun
        String msBuildPath = project.hasProperty("msbuildPath") ? project.property("msbuildPath") : null
        String nugetPath = project.hasProperty("nugetPath") ? project.property("nugetPath") : null
        String nugetVersion = project.hasProperty("nugetVersion") ? project.property("nugetVersion") : null

        if (dryRun) {
            instance = [getXbuild : { new XBuild(new FakeCommandRunner())},
                        getNuget : { new DefaultNuget(new FakeCommandRunner())},
                        getFileCopier : { new FakeFileCopier()}] as ApplicationContext
        } else {
            instance =  [getXbuild : { new XBuild(new SystemCommandRunner(), msBuildPath)},
                         getNuget : { new DefaultNuget(new SystemCommandRunner(), nugetVersion, nugetPath)},
                         getFileCopier : { new DefaultFileCopier()}] as ApplicationContext
        }
    }

    interface ApplicationContext {
        XBuild getXbuild()
        Nuget getNuget()
        FileCopier getFileCopier()
    }

}
