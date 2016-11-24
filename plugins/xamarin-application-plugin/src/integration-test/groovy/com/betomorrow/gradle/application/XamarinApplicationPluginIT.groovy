package com.betomorrow.gradle.application

import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.junit.Test

class XamarinApplicationPluginIT {

    @Test
     void testRunRealScript() {
        File sampleDir = new File("../../sample/CrossApp")
        BuildResult result = GradleRunner.create()
                .withProjectDir(sampleDir)
                .withDebug(true)
                .withArguments("buildAndroid", "--stacktrace")
                .build()

        println(result.getOutput())
    }
}
