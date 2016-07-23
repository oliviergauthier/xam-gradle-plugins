package com.betomorrow.msbuild.tools.solution

import org.junit.Test

/**
 * Created by olivier on 14/06/16.
 */
class SolutionLoaderTest {

    String SAMPLE_SOLUTION = ClassLoader.getSystemResource('CrossApp/CrossApp.sln').file;

    @Test
    public void testLoadRealSolution() {
        def descriptor = new SolutionLoader().load(SAMPLE_SOLUTION);

        assert descriptor.getProjects().size() == 3;
        assert descriptor.getProject('CrossApp') != null;

        def iosApp = descriptor.getProject('CrossApp.iOS');
        assert  iosApp != null;
        assert iosApp.isAndroidApplication() == false;
        assert iosApp.isIosApplication() == true;
        assert iosApp.getAssemblyName() == 'CrossApp.iOS'
        assert iosApp.getOutputPath('Release', 'iPhone') == 'bin\\iPhone\\Release'
        assert iosApp.getOutputPath('Debug', 'iPhone') == 'bin\\iPhone\\Debug'

        def androidApp = descriptor.getProject('CrossApp.Droid');
        assert androidApp != null;
        assert androidApp.isAndroidApplication() == true;
        assert androidApp.isIosApplication() == false;
        assert androidApp.getAssemblyName() == 'CrossApp.Droid'
        assert androidApp.getOutputPath('Release') == 'bin\\Release'
        assert androidApp.getOutputPath('Debug|iPhone') == 'bin\\Debug'

    }
}
