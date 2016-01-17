package com.betomorrow.msbuild.tools.xbuild;

import org.junit.Test;

/**
 * Created by Olivier on 15/01/2016.
 */
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
        assert ['xbuild', '/t:Build', '/p:Configuration=Release', '/p:DebugType=Full', 'toto.csproj'].containsAll(xbuild.build())
    }
}