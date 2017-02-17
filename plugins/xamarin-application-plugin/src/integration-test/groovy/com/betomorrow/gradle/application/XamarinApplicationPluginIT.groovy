package com.betomorrow.gradle.application

import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.junit.Test

class XamarinApplicationPluginIT {


    @Test
    void testCleanScript() {
        File sampleDir = new File("../../sample/CrossApp")
        BuildResult result = GradleRunner.create()
                .withProjectDir(sampleDir)
                .withDebug(true)
                .withPluginClasspath()
                .withArguments("clean", "--stacktrace", "-PdryRun=true")
                .build()

        println(result.getOutput())
    }

    @Test
    void testBuildAndroidScript() {
        File sampleDir = new File("../../sample/CrossApp")
        BuildResult result = GradleRunner.create()
                .withProjectDir(sampleDir)
                .withDebug(true)
                .withPluginClasspath()
                .withArguments("buildAndroid", "--stacktrace", "-PdryRun=true")
                .build()

        println(result.getOutput())
    }

    @Test
    void testBuildIosScript() {
        File sampleDir = new File("../../sample/CrossApp")
        BuildResult result = GradleRunner.create()
                .withProjectDir(sampleDir)
                .withDebug(true)
                .withPluginClasspath()
                .withArguments("buildIos", "--stacktrace", "-PdryRun=true")
                .build()

        println(result.getOutput())
    }
}
