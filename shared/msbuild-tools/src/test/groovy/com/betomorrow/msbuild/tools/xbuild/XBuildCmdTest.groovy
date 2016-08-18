package com.betomorrow.msbuild.tools.xbuild;

import org.junit.Test;

public class XBuildCmdTest {

    @Test
    public void testBuildReturnsCorrectCommand() throws Exception {
        def xbuild = new XBuildCmd(target: 'Build', configuration: 'Release', projectPath: 'toto.csproj')
        assert xbuild.build() == ['xbuild', '/t:Build', '/p:Configuration=Release', 'toto.csproj']
    }

    @Test
    public void testBuildReturnsCommandWithExtraProperties() throws Exception {
        def xbuild = new XBuildCmd(target: 'Build', configuration: 'Release', projectPath: 'toto.csproj')
        xbuild.addProperty('DebugType', 'Full')

        def args = xbuild.build().collect{ it.toString() };
        assert ['xbuild', '/t:Build', '/p:DebugType=Full', '/p:Configuration=Release', 'toto.csproj'].containsAll(args)
    }
}