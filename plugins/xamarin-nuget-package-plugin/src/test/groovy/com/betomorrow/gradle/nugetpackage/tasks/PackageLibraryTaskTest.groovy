package com.betomorrow.gradle.nugetpackage.tasks

import com.betomorrow.msbuild.tools.nuget.Nuget
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class PackageLibraryTaskTest extends Specification {

    Nuget nuget

    Project project
    PackageLibraryTask task

    def "setup"() {
        nuget = Mock()

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
        project.evaluate()
        task = project.package
        task.nuget = nuget

        when:
        task.packageLibrary()

        then:
        1 * nuget.pack("default.nuspec", 'nightly')
    }

    def "package don't use suffix by default"() {
        given:
        project.nuspec {
            packageId = 'Com.Acme.CrossLib'
            packageName = 'CrossLib'
        }
        project.evaluate()
        task = project.package
        task.nuget = nuget

        when:
        task.packageLibrary()

        then:
        1 * nuget.pack("default.nuspec", null)
    }
}
