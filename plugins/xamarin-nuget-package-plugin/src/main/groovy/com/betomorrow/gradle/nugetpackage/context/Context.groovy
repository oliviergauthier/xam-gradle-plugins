package com.betomorrow.gradle.nugetpackage.context

import com.betomorrow.msbuild.tools.commands.FakeCommandRunner
import com.betomorrow.msbuild.tools.commands.SystemCommandRunner
import groovy.transform.PackageScope
import com.betomorrow.msbuild.tools.nuget.DefaultNuget

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
        return [getNuget : { new DefaultNuget(new FakeCommandRunner())}] as ApplicationContext
    }

    private static ApplicationContext createRealApplicationContext() {
        return [getNuget : { new DefaultNuget(new SystemCommandRunner())}] as ApplicationContext
    }


}
