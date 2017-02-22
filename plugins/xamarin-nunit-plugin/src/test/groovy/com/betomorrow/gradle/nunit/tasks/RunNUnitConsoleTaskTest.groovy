package com.betomorrow.gradle.nunit.tasks

import com.betomorrow.msbuild.tools.nunit.NUnitConsole
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

import java.nio.file.Paths

class RunNUnitConsoleTaskTest extends Specification {

    Project project

    RunNUnitConsoleTask task
    NUnitConsole runner

    def "setup"(){
        runner = Mock()

        project = ProjectBuilder.builder().withProjectDir(new File('src/test/resources/CrossLib')).build()
        project.apply plugin: 'xamarin-nunit-plugin'

        project.solution = 'CrossLib.sln'
        project.configuration = 'Release'
    }

    def "run should resolve projects assemblies"() {
        given:
        project.nunit  {
            projects = 'CrossLib.Test'
        }

        when:
        project.evaluate()
        task = project.test
        task.nUnitRunner = runner
        task.run()

        then:
        1 * runner.run([Paths.get('CrossLib.Test/bin/Release/CrossLib.Test.dll').toString()], null)
    }

    def "run should use given assemblies"() {
        given:
        project.nunit  {
            assemblies = 'CrossLib.Test/bin/Release/CrossLib.Test.dll'
        }

        when:
        project.evaluate()
        task = project.test
        task.nUnitRunner = runner
        task.run()

        then:
        1 * runner.run(['CrossLib.Test/bin/Release/CrossLib.Test.dll'], null)
    }

    def "run should use TestResults format"() {
        given:
        project.nunit  {
            assemblies = 'CrossLib.Test/bin/Release/CrossLib.Test.dll'
            format = "nunit2"
        }

        when:
        project.evaluate()
        task = project.test
        task.nUnitRunner = runner
        task.run()

        then:
        1 * runner.run(_, "nunit2")
    }
}
