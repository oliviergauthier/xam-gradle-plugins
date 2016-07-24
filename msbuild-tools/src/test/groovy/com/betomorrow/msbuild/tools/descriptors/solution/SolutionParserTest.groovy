package com.betomorrow.msbuild.tools.descriptors.solution

import org.junit.Test


/**
 * Created by olivier on 06/03/16.
 */
public class SolutionParserTest {

    String SAMPLE_SOLUTION = ClassLoader.getSystemResource('Sample.sln').file;

    @Test
    public void testLoadSolutionFile() {
        def solution = new SolutionParser();
        def projects = solution.parse(SAMPLE_SOLUTION);

        assert projects[0] == new SolutionProject("CrossApp", "CrossApp/CrossApp.csproj")
        assert projects[1] == new SolutionProject("CrossApp.iOS", "iOS/CrossApp.iOS.csproj")
        assert projects[2] == new SolutionProject("CrossApp.Droid", "Droid/CrossApp.Droid.csproj")
    }

}