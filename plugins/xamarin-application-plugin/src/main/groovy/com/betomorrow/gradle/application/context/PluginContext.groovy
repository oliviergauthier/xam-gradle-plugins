package com.betomorrow.gradle.application.context

import com.betomorrow.xamarin.android.manifest.AndroidManifestWriter
import com.betomorrow.xamarin.android.manifest.DefaultAndroidManifestWriter
import com.betomorrow.xamarin.android.manifest.FakeAndroidManifestWriter
import com.betomorrow.xamarin.ios.plist.DefaultInfoPlistWriter
import com.betomorrow.xamarin.ios.plist.FakeInfoPlistWriter
import com.betomorrow.xamarin.ios.plist.InfoPlistWriter
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
            instance = [getFileCopier : { new FakeFileCopier() },
                        getAndroidManifestWriter : { new FakeAndroidManifestWriter() },
                        getInfoPlistWriter : { new FakeInfoPlistWriter()},
                        getXbuild : { new XBuild(new FakeCommandRunner(), msBuildPath)},
                        getNuget : { new DefaultNuget(new FakeCommandRunner(), nugetVersion, nugetPath)}] as ApplicationContext
        } else {
            instance = [getFileCopier : { new DefaultFileCopier() },
                        getAndroidManifestWriter : { new DefaultAndroidManifestWriter() },
                        getInfoPlistWriter : { new DefaultInfoPlistWriter()},
                        getXbuild : { new XBuild(new SystemCommandRunner())},
                        getNuget : { new DefaultNuget(new SystemCommandRunner(), nugetVersion, nugetPath)}] as ApplicationContext
        }
    }

    interface ApplicationContext {
        FileCopier getFileCopier()
        AndroidManifestWriter getAndroidManifestWriter()
        InfoPlistWriter getInfoPlistWriter()
        XBuild getXbuild()
        Nuget getNuget()
    }

}
