package com.betomorrow.xamarin.tools.xbuild

import com.betomorrow.xamarin.commands.CommandRunner
import org.junit.Before
import org.junit.Test

class XBuildTest {

    XBuild xBuild
    CommandRunner.Cmd executedCmd

    @Before
    void SetUp() {
        xBuild = new XBuild()
        xBuild.commandRunner = [ run: { cmd ->  executedCmd = cmd; return 0 } ] as CommandRunner
    }

    @Test
    void testBuildAndroidApp() {
        xBuild.buildAndroidApp("Release", "sample.csproj")
        assert executedCmd.build() == ['/Library/Frameworks/Mono.framework/Commands/msbuild', '/t:PackageForAndroid', '/p:Configuration=Release', 'sample.csproj']
    }


    @Test
    void testBuildIosApp() {
        xBuild.buildIosApp("Release", "iPhone", "bin/iPhone/Release", "sample.sln")
        assert executedCmd.build().containsAll(['/Library/Frameworks/Mono.framework/Commands/msbuild', '/t:Build', '/p:IpaPackageDir=bin/iPhone/Release',
                                                '/p:Configuration=Release', '/p:Platform=iPhone', 'sample.sln'])

    }

}
