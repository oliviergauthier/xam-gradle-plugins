package com.betomorrow.gradle.library.context

import com.betomorrow.msbuild.tools.commands.FakeCommandRunner
import com.betomorrow.msbuild.tools.commands.SystemCommandRunner
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
        return [getXbuild : { new XBuild(new FakeCommandRunner())}] as ApplicationContext
    }

    private static ApplicationContext createRealApplicationContext() {
        return [getXbuild : { new XBuild(new SystemCommandRunner())}] as ApplicationContext
    }
}
