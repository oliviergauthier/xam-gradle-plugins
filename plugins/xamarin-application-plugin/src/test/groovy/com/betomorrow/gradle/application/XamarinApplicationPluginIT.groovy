package com.betomorrow.gradle.application

import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.junit.Test

class XamarinApplicationPluginIT {


    @Test
    public void testRunRealScript() {
        File sampleDir = new File("../../sample/CrossApp");
        BuildResult result = GradleRunner.create()
                .withProjectDir(sampleDir)
                .withDebug(true)
                .withArguments("tasks", "--stacktrace")
                .build();

        println(result.getOutput());

    }
}
