package com.betomorrow.xamarin.tools.xbuild

import org.junit.Test

class XBuildCmdTest {

    @Test
    void testBuildReturnsCorrectCommand() throws Exception {
        XBuildCmd xbuild = new XBuildCmd('/Library/Frameworks/Mono.framework/Commands/msbuild')
        xbuild.target = 'Build'
        xbuild.configuration = 'Release'
        xbuild.projectPath = 'toto.csproj'
        assert xbuild.build() == ['/Library/Frameworks/Mono.framework/Commands/msbuild', '/t:Build', '/p:Configuration=Release', 'toto.csproj']
    }

    @Test
    void testBuildReturnsCommandWithExtraProperties() throws Exception {
        XBuildCmd xbuild = new XBuildCmd('/Library/Frameworks/Mono.framework/Commands/msbuild')
        xbuild.target = 'Build'
        xbuild.configuration = 'Release'
        xbuild.projectPath = 'toto.csproj'
        xbuild.addProperty('DebugType', 'Full')

        def args = xbuild.build().collect{ it.toString() }
        assert ['/Library/Frameworks/Mono.framework/Commands/msbuild', '/t:Build', '/p:DebugType=Full', '/p:Configuration=Release', 'toto.csproj'].containsAll(args)
    }

}
