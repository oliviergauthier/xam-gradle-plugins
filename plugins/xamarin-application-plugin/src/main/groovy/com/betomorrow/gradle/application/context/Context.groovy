package com.betomorrow.gradle.application.context

import com.betomorrow.android.tools.DefaultAndroidManifestEditorFactory
import com.betomorrow.android.tools.FakeAndroidManifestEditorFactory
import com.betomorrow.msbuild.tools.Files.DefaultFileCopier
import com.betomorrow.msbuild.tools.Files.FakeFileCopier
import com.betomorrow.msbuild.tools.commands.FakeCommandRunner
import com.betomorrow.msbuild.tools.commands.SystemCommandRunner
import groovy.transform.PackageScope

class Context {

    private static ApplicationContext instance;

    @PackageScope
    protected static ApplicationContext dryRunContext = createFakeApplicationContext();

    @PackageScope
    protected static ApplicationContext defaultContext = createRealApplicationContext();

    static {
        configure(false);
    }

    static ApplicationContext getCurrent() {
        return instance;
    }

    static void configure(boolean dryRun) {
        if (dryRun) {
            instance = dryRunContext;
        } else {
            instance = defaultContext;
        }
    }

    private static ApplicationContext createFakeApplicationContext() {
        return [getFileCopier : { new FakeFileCopier() },
                getCommandRunner : { new FakeCommandRunner() },
                getAndroidManifestEditorFactory : { new FakeAndroidManifestEditorFactory() }] as ApplicationContext;
    }

    private static ApplicationContext createRealApplicationContext() {
        return [getFileCopier : { new DefaultFileCopier() },
                getCommandRunner : { new SystemCommandRunner() },
                getAndroidManifestEditorFactory : { new DefaultAndroidManifestEditorFactory() }] as ApplicationContext;
    }


}
