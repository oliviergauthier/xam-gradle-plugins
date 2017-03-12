package com.betomorrow.gradle.library.tasks

import com.betomorrow.msbuild.tools.files.FileCopier
import com.betomorrow.msbuild.tools.nuget.Nuget
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class PackageLibraryTaskTest extends Specification {

    Nuget nuget
    FileCopier fileCopier

    Project project
    PackageLibraryTask task

    def "setup"() {
        nuget = Mock()
        fileCopier = Mock()

        project = ProjectBuilder.builder().withProjectDir(new File('src/test/resources/CrossLib')).build()
        project.apply plugin: 'xamarin-library-plugin'
    }

    def "package use right data"() {
        given:
        project.nuspec {
            packageId = 'Com.Acme.CrossLib'
            packageName = 'CrossLib'
            suffix = 'nightly'
        }
        project.version = "1.0.0"
        project.evaluate()
        task = project.package
        task.nuget = nuget
        task.fileCopier = fileCopier

        when:
        task.packageLibrary()

        then:
        1 * nuget.pack(project.file("generated.nuspec").absolutePath, 'nightly')
        1 * fileCopier.move(project.file("Com.Acme.CrossLib.1.0.0-nightly.nupkg").toPath(), project.file("dist/Com.Acme.CrossLib.1.0.0-nightly.nupkg").toPath())
    }

    def "package don't use suffix by default"() {
        given:
        project.nuspec {
            packageId = 'Com.Acme.CrossLib'
            packageName = 'CrossLib'
        }
        project.version = "1.0.0"
        project.evaluate()
        task = project.package
        task.nuget = nuget
        task.fileCopier = fileCopier

        when:
        task.packageLibrary()

        then:
        1 * nuget.pack(project.file("generated.nuspec").absolutePath, null)
        1 * fileCopier.move(project.file("Com.Acme.CrossLib.1.0.0.nupkg").toPath(), project.file("dist/Com.Acme.CrossLib.1.0.0.nupkg").toPath())
    }

}
