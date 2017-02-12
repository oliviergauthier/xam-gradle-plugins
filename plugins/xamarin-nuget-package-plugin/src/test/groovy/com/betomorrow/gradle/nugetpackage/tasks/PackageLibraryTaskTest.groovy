package com.betomorrow.gradle.nugetpackage.tasks

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder

class PackageLibraryTaskTest {

    Project project
    PackageLibraryTask task

    def "setup"() {
        project = ProjectBuilder.builder().withProjectDir(new File('src/test/resources/CrossLib')).build()
        project.apply plugin: 'xamarin-nuget-package-plugin'

        project.solution = 'CrossLib.sln'
        project.configuration = 'Release'
    }

    def "package use right data"() {
        given:
        project.nuspec {
            packageId = 'Com.Acme.CrossLib'
            packageName = 'CrossLib'
            suffix = 'nightly'
        }

        when:
        project.evaluate()
        task = project.package
        task.package()

        then:
        assert true
    }

}
