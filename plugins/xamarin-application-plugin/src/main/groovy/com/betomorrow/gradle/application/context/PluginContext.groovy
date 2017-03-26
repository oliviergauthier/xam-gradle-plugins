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
        configure(project.hasProperty("dryRun") && project.dryRun)
    }

    static void configure(boolean dryRun) {
        if (dryRun) {
            instance = [getFileCopier : { new FakeFileCopier() },
                        getAndroidManifestWriter : { new FakeAndroidManifestWriter() },
                        getInfoPlistWriter : { new FakeInfoPlistWriter()},
                        getXbuild : { new XBuild(new FakeCommandRunner())},
                        getNuget : { new DefaultNuget(new FakeCommandRunner())}] as ApplicationContext
        } else {
            instance = [getFileCopier : { new DefaultFileCopier() },
                        getAndroidManifestWriter : { new DefaultAndroidManifestWriter() },
                        getInfoPlistWriter : { new DefaultInfoPlistWriter()},
                        getXbuild : { new XBuild(new SystemCommandRunner())},
                        getNuget : { new DefaultNuget(new SystemCommandRunner())}] as ApplicationContext
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
