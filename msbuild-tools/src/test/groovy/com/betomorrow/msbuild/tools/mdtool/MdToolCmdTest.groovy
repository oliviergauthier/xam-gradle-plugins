package com.betomorrow.msbuild.tools.mdtool

import org.junit.Test

/**
 * Created by olivier on 17/01/16.
 */
class MdToolCmdTest {

    @Test
    public void testBuildReturnsCorrectCommand() throws Exception {
        def mDToolCmd = new MdToolCmd(configuration: 'Release', platform: 'iPhone', verbose: true, solutionPath: 'toto.sln')
        assert mDToolCmd.build() == ['mdtool', '-v', 'build', '--configuration:Release|iPhone', 'toto.sln']
    }

}
