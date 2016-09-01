package com.betomorrow.msbuild.tools.descriptors.solution

import com.betomorrow.msbuild.tools.Files.FileUtils
import org.junit.Test

public class SolutionParserTest {

    String SAMPLE_SOLUTION = FileUtils.getResourcePath('Sample.sln');

    @Test
    public void testLoadSolutionFile() {
        def solution = new SolutionParser();

        def projects = solution.parse(SAMPLE_SOLUTION);

        assert projects[0] == new SolutionProject("CrossApp", "CrossApp/CrossApp.csproj")
        assert projects[1] == new SolutionProject("CrossApp.iOS", "iOS/CrossApp.iOS.csproj")
        assert projects[2] == new SolutionProject("CrossApp.Droid", "Droid/CrossApp.Droid.csproj")
    }

}