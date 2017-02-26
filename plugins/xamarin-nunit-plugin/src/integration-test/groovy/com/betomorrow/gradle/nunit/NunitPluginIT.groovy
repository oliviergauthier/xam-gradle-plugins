package com.betomorrow.gradle.nunit

import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import spock.lang.Specification

class NunitPluginIT extends Specification {

    GradleRunner runner

    def "setup"() {
        File sampleDir = new File("../../sample/Nunit")
        runner = GradleRunner.create()
                .withProjectDir(sampleDir)
                .withDebug(true)
                .withPluginClasspath()
    }

    def "test nunit"() {
        when:
        BuildResult result = runner
                .withArguments("test", "--stacktrace", "-PdryRun=true")
                .build()

        then:
        println(result.getOutput())
    }
}
