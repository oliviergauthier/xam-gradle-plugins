package com.betomorrow.gradle.application.context

import com.betomorrow.android.tools.manifest.DefaultAndroidManifestWriter
import com.betomorrow.android.tools.manifest.FakeAndroidManifestWriter
import com.betomorrow.ios.tools.plist.DefaultInfoPlistWriter
import com.betomorrow.ios.tools.plist.FakeInfoPlistWriter
import com.betomorrow.msbuild.tools.Files.DefaultFileCopier
import com.betomorrow.msbuild.tools.Files.FakeFileCopier
import com.betomorrow.msbuild.tools.commands.FakeCommandRunner
import com.betomorrow.msbuild.tools.commands.SystemCommandRunner
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

    static void configure(ApplicationContext newContext) {
        defaultContext = newContext
        dryRunContext = newContext
        instance = newContext
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
                getCommandRunner : { new FakeCommandRunner() },
                getAndroidManifestWriter : { new FakeAndroidManifestWriter() },
                getInfoPlistWriter : { new FakeInfoPlistWriter()}] as ApplicationContext
    }

    private static ApplicationContext createRealApplicationContext() {
        return [getFileCopier : { new DefaultFileCopier() },
                getCommandRunner : { new SystemCommandRunner() },
                getAndroidManifestWriter : { new DefaultAndroidManifestWriter() },
                getInfoPlistWriter : { new DefaultInfoPlistWriter()}] as ApplicationContext
    }


}
