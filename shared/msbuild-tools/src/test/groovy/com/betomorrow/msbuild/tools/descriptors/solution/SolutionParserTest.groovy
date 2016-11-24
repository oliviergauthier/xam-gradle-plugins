package com.betomorrow.msbuild.tools.descriptors.solution

import com.betomorrow.msbuild.tools.Files.FileUtils
import org.junit.Test

import java.nio.file.Path

class SolutionParserTest {

    Path SAMPLE_SOLUTION = FileUtils.getResourcePath('Sample.sln')

    @Test
     void testLoadSolutionFile() {
        def solution = new SolutionParser()

        def projects = solution.parse(SAMPLE_SOLUTION)

        def project = projects[0]
        assert project.name == "CrossApp"
        assert project.path ==  "CrossApp/CrossApp.csproj"

        project = projects[1]
        assert project.name == "CrossApp.iOS"
        assert project.path ==  "iOS/CrossApp.iOS.csproj"

        project = projects[2]
        assert project.name == "CrossApp.Droid"
        assert project.path ==  "Droid/CrossApp.Droid.csproj"

    }

}