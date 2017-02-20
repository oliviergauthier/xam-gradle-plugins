package com.betomorrow.gradle.application.context

import com.betomorrow.android.manifest.AndroidManifestWriter
import com.betomorrow.android.manifest.DefaultAndroidManifestWriter
import com.betomorrow.android.manifest.FakeAndroidManifestWriter
import com.betomorrow.gradle.commons.context.Context
import com.betomorrow.ios.plist.DefaultInfoPlistWriter
import com.betomorrow.ios.plist.FakeInfoPlistWriter
import com.betomorrow.ios.plist.InfoPlistWriter
import com.betomorrow.msbuild.tools.commands.FakeCommandRunner
import com.betomorrow.msbuild.tools.commands.SystemCommandRunner
import com.betomorrow.msbuild.tools.files.DefaultFileCopier
import com.betomorrow.msbuild.tools.files.FakeFileCopier
import com.betomorrow.msbuild.tools.files.FileCopier
import com.betomorrow.xamarin.xbuild.XBuild

class PluginContext extends Context<ApplicationContext> {

    static {
        dryRunContext = [getFileCopier : { new FakeFileCopier() },
                         getAndroidManifestWriter : { new FakeAndroidManifestWriter() },
                         getInfoPlistWriter : { new FakeInfoPlistWriter()},
                         getXbuild : { new XBuild(new FakeCommandRunner())}] as ApplicationContext

        defaultContext = [getFileCopier : { new DefaultFileCopier() },
                          getAndroidManifestWriter : { new DefaultAndroidManifestWriter() },
                          getInfoPlistWriter : { new DefaultInfoPlistWriter()},
                          getXbuild : { new XBuild(new SystemCommandRunner())}] as ApplicationContext
    }

    interface ApplicationContext {
        FileCopier getFileCopier()
        AndroidManifestWriter getAndroidManifestWriter()
        InfoPlistWriter getInfoPlistWriter()
        XBuild getXbuild()
    }

}
