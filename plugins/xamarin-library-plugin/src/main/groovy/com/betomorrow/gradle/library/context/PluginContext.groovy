package com.betomorrow.gradle.library.context

import com.betomorrow.gradle.commons.context.Context
import com.betomorrow.msbuild.tools.commands.FakeCommandRunner
import com.betomorrow.msbuild.tools.commands.SystemCommandRunner
import com.betomorrow.xamarin.xbuild.XBuild

class PluginContext extends Context<ApplicationContext> {

    static {
        dryRunContext =  [getXbuild : { new XBuild(new FakeCommandRunner())}] as ApplicationContext

        defaultContext = [getXbuild : { new XBuild(new SystemCommandRunner())}] as ApplicationContext
    }

    interface ApplicationContext {
        XBuild getXbuild()
    }

}
