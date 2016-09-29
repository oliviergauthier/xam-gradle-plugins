package com.betomorrow.msbuild.tools.descriptors.solution

import com.betomorrow.msbuild.tools.Files.FileUtils
import org.junit.Test

import java.nio.file.Paths

class SolutionLoaderTest {

    def SAMPLE_SOLUTION = FileUtils.getResourcePath('CrossApp/CrossApp.sln');

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
        assert iosApp.getOutputDir('Release', 'iPhone').toString() == SAMPLE_SOLUTION.parent.resolve('iOS/bin/iPhone/Release').toString()
        assert iosApp.getOutputDir('Debug', 'iPhone').toString() == SAMPLE_SOLUTION.parent.resolve('iOS/bin/iPhone/Debug').toString()

        def androidApp = descriptor.getProject('CrossApp.Droid');
        assert androidApp != null;
        assert androidApp.isAndroidApplication();
        assert !androidApp.isIosApplication();
        assert androidApp.getAssemblyName() == 'CrossApp.Droid'
        assert androidApp.getOutputDir('Release') == SAMPLE_SOLUTION.parent.resolve('Droid/bin/Release')
        assert androidApp.getOutputDir('Debug|iPhone') == SAMPLE_SOLUTION.parent.resolve('Droid/bin/Debug')

    }
}
