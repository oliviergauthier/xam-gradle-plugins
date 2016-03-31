package com.betomorrow.msbuild.tools.solution

import org.apache.commons.io.input.BOMInputStream
import org.junit.Test

import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Created by olivier on 06/03/16.
 */
public class SolutionFileTest {

    String SAMPLE_SOLUTION = ClassLoader.getSystemResource('Sample.sln').file;

    @Test
    public void testLoadSolutionFile() {
        def solution = new SolutionFile(SAMPLE_SOLUTION);
        def projects = solution.getProjects();

        assert projects[0] == new ProjectSolution("CrossApp", "CrossApp/CrossApp.csproj")
        assert projects[1] == new ProjectSolution("CrossApp.iOS", "iOS/CrossApp.iOS.csproj")
        assert projects[2] == new ProjectSolution("CrossApp.Droid", "Droid/CrossApp.Droid.csproj")
    }

}