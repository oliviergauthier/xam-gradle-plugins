package com.betomorrow.gradle.nunit

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class NUnitPluginTest extends Specification {

    Project project


    def "setup"(){
        project = ProjectBuilder.builder().withProjectDir(new File('src/test/resources/CrossLib')).build()
        project.apply plugin: 'xamarin-nunit-plugin'

        project.solution = 'CrossLib.sln'
        project.configuration = 'Release'
    }


    def "test task should depends on compileTest when projects are defined"() {
        given:
        project.nunit.projects = "CrossLib.Test"

        project.evaluate()

        expect:
        project.test.getDependsOn().contains(project.compileTest)
    }

    def "test task should not depends on compileTest when assemblies are defined"() {
        given:
        project.nunit.assemblies = "test.dll"

        project.evaluate()

        expect:
        project.test.getDependsOn().contains(project.compileTest) == false
    }

    def "default test projects should be found by name convention"() {
        given:
        project.evaluate()

        expect:
        project.nunit.projects == ['CrossLib.Test']
    }
}
