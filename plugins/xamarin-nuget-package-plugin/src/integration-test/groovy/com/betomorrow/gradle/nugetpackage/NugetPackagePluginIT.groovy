package com.betomorrow.gradle.nugetpackage

import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.junit.Test

class NugetPackagePluginIT {


    @Test
    void testGenerateNuspec() {
        File sampleDir = new File("../../sample/Nuspec")
        BuildResult result = GradleRunner.create()
                .withProjectDir(sampleDir)
                .withDebug(true)
                .withPluginClasspath()
                .withArguments("generateNuspec", "--stacktrace")
                .build()

        println(result.getOutput())
    }

}
