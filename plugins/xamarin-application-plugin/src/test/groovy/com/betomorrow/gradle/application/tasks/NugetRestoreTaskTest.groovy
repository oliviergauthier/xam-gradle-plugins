package com.betomorrow.gradle.application.tasks

import com.betomorrow.xamarin.tools.nuget.Nuget
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class NugetRestoreTaskTest extends Specification {

    Project project

    NugetRestoreTask task

    Nuget nuget

    def "setup"() {
        nuget = Mock()

        project = ProjectBuilder.builder().withProjectDir(new File('src/test/resources/CrossApp')).build()
        project.apply plugin: 'xamarin-application-plugin'
    }

    def "restore should call nuget"(){
        given:

        when:
        project.evaluate()
        task = project.tasks.nugetRestore
        task.nuget = nuget
        task.restore()

        then:
        1 * nuget.restore()
    }

}
