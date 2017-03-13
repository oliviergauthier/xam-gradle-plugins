package com.betomorrow.gradle.library.tasks

import com.betomorrow.msbuild.tools.files.FileCopier
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
        project.apply plugin: 'xamarin-library-plugin'
    }

    def "package use right data"() {
        given:
        project.nuspec {
            packageId = 'Com.Acme.CrossLib'
            suffix = 'nightly'
        }
        project.version = "1.0.0"
        project.evaluate()
        task = project.package
        task.nuget = nuget

        when:
        task.packageLibrary()

        then:
        1 * nuget.pack(project.file("generated.nuspec").absolutePath, 'nightly', 'dist')
    }

    def "package don't use suffix by default"() {
        given:
        project.nuspec {
            packageId = 'Com.Acme.CrossLib'
        }
        project.version = "1.0.0"
        project.evaluate()
        task = project.package
        task.nuget = nuget

        when:
        task.packageLibrary()

        then:
        1 * nuget.pack(project.file("generated.nuspec").absolutePath, null, 'dist')
    }

}
