package com.betomorrow.gradle.library

import com.betomorrow.gradle.commons.tasks.CleanTask
import com.betomorrow.gradle.library.tasks.BuildTask
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class XamarinLibraryPluginTest extends Specification {

    Project project

    def "setup"() {
        project = ProjectBuilder.builder().withProjectDir(new File('src/test/resources/')).build()
        project.apply plugin: 'xamarin-library-plugin'

        project.solution = 'CrossLib/CrossLib.sln'
        project.configuration = 'Debug'

        project.library {
        }

        project.evaluate()
    }

    def "should create build task"() {
        when:
        def task = project.tasks.build

        then:
        assert task instanceof BuildTask
        assert task.solutionFile == 'CrossLib/CrossLib.sln'
        assert task.configuration == 'Debug'
    }

    def "should create build task with release configuration by default"() {
        given:
        project = ProjectBuilder.builder().withProjectDir(new File('src/test/resources/')).build()
        project.apply plugin: 'xamarin-library-plugin'

        project.solution = 'CrossLib/CrossLib.sln'

        project.library {
        }

        project.evaluate()

        when:
        def task = project.tasks.build

        then:
        assert task instanceof BuildTask
        assert task.solutionFile == 'CrossLib/CrossLib.sln'
        assert task.configuration == 'Release'


    }

    def "should create clean task"() {
        when:
        def task = project.tasks.clean

        then:
        assert task instanceof CleanTask
        assert task.solutionFile == 'CrossLib/CrossLib.sln'
    }


}
