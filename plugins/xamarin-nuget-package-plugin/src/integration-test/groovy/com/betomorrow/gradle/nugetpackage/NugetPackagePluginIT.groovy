package com.betomorrow.gradle.nugetpackage


import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import spock.lang.Specification


class NugetPackagePluginIT extends Specification {

    GradleRunner runner

    void "setup"() {
        File sampleDir = new File("../../sample/Nuspec")
        runner = GradleRunner.create()
                .withProjectDir(sampleDir)
                .withDebug(true)
                .withPluginClasspath()
    }

    void "test generate nuspec"() {
        when:
        BuildResult result = runner
                .withArguments("generateNuspec", "--stacktrace", "-PdryRun=true")
                .build()

        then:
        println(result.getOutput())
    }

    void "test pakage library"() {
        when:
        BuildResult result = runner
                .withArguments("package", "--stacktrace", "-PdryRun=true")
                .build()

        then:
        println(result.getOutput())
    }

    void "test install library"() {
        when:
        BuildResult result = runner
                .withArguments("install", "--stacktrace", "-PdryRun=true")
                .build()

        then:
        println(result.getOutput())
    }

    void "test deploy library"() {
        when:
        BuildResult result = runner
                .withArguments("deploy", "--stacktrace", "-PdryRun=true")
                .build()

        then:
        println(result.getOutput())
    }

}
