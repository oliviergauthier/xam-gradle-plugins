package com.betomorrow.gradle.application.context

import com.betomorrow.android.manifest.DefaultAndroidManifestWriter
import com.betomorrow.android.manifest.FakeAndroidManifestWriter
import com.betomorrow.ios.plist.DefaultInfoPlistWriter
import com.betomorrow.ios.plist.FakeInfoPlistWriter
import com.betomorrow.msbuild.tools.commands.FakeCommandRunner
import com.betomorrow.msbuild.tools.commands.SystemCommandRunner
import com.betomorrow.msbuild.tools.files.DefaultFileCopier
import com.betomorrow.msbuild.tools.files.FakeFileCopier
import com.betomorrow.xamarin.xbuild.XBuild
import groovy.transform.PackageScope

class Context {

    private static ApplicationContext instance

    @PackageScope
    protected static ApplicationContext dryRunContext = createFakeApplicationContext()

    @PackageScope
    protected static ApplicationContext defaultContext = createRealApplicationContext()

    static {
        configure(false)
    }

    static ApplicationContext getCurrent() {
        return instance
    }

    static void configure(boolean dryRun) {
        if (dryRun) {
            instance = dryRunContext
        } else {
            instance = defaultContext
        }
    }

    private static ApplicationContext createFakeApplicationContext() {
        return [getFileCopier : { new FakeFileCopier() },
                getAndroidManifestWriter : { new FakeAndroidManifestWriter() },
                getInfoPlistWriter : { new FakeInfoPlistWriter()},
                getXbuild : { new XBuild(new FakeCommandRunner())}] as ApplicationContext
    }

    private static ApplicationContext createRealApplicationContext() {
        return [getFileCopier : { new DefaultFileCopier() },
                getAndroidManifestWriter : { new DefaultAndroidManifestWriter() },
                getInfoPlistWriter : { new DefaultInfoPlistWriter()},
                getXbuild : { new XBuild(new SystemCommandRunner())}] as ApplicationContext
    }


}
