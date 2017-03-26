package com.betomorrow.msbuild.tools.mdtool

import org.junit.Test


class MdToolCmdTest {

    @Test
     void testBuildReturnsCorrectCommand() throws Exception {
        def mDToolCmd = new MdToolCmd(configuration: 'Release', platform: 'iPhone', verbose: true, solutionPath: 'toto.sln')
        assert mDToolCmd.build() == ['mdtool', '-v', 'build', '--configuration:Release|iPhone', 'toto.sln']
    }

}
