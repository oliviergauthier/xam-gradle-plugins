package com.betomorrow.msbuild.tools.descriptors.solution

import com.betomorrow.msbuild.tools.FileUtils
import org.junit.Test

class SolutionLoaderTest {

    String SAMPLE_SOLUTION = FileUtils.getResourcePath('CrossApp/CrossApp.sln');

    @Test
    public void testLoadRealSolution() {
        def descriptor = new SolutionLoader().load(SAMPLE_SOLUTION);

        assert descriptor.getProjects().size() == 3;
        assert descriptor.getProject('CrossApp') != null;

        def iosApp = descriptor.getProject('CrossApp.iOS');
        assert  iosApp != null;
        assert !iosApp.isAndroidApplication();
        assert iosApp.isIosApplication();
        assert iosApp.getAssemblyName() == 'CrossApp.iOS'
        assert iosApp.getOutputPath('Release', 'iPhone') == 'bin\\iPhone\\Release'
        assert iosApp.getOutputPath('Debug', 'iPhone') == 'bin\\iPhone\\Debug'

        def androidApp = descriptor.getProject('CrossApp.Droid');
        assert androidApp != null;
        assert androidApp.isAndroidApplication();
        assert !androidApp.isIosApplication();
        assert androidApp.getAssemblyName() == 'CrossApp.Droid'
        assert androidApp.getOutputPath('Release') == 'bin\\Release'
        assert androidApp.getOutputPath('Debug|iPhone') == 'bin\\Debug'

    }
}
