package com.betomorrow.gradle.library

import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.junit.Test

class XamarinLibraryPluginIT {

    @Test
    void testRunRealScript() {
        File sampleDir = new File("../../sample/CrossLib")
        BuildResult result = GradleRunner.create()
                .withProjectDir(sampleDir)
                .withDebug(true)
//                .withPluginClasspath()
                .withArguments("build", "--stacktrace", "-PdryRun=true")
                .build()

        println(result.getOutput())
    }

}
