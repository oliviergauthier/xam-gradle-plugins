package com.betomorrow.gradle.library.tasks

import com.betomorrow.msbuild.tools.nuget.Nuget
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class PushPackageTaskTest extends Specification {

    Nuget nuget

    Project project
    PushPackageTask task

    def "setup"() {
        nuget = Mock()

        project = ProjectBuilder.builder()
                .withProjectDir(new File('src/test/resources/CrossLib')).build()
        project.apply plugin: 'xamarin-library-plugin'

        project.solution = 'CrossLib.sln'
        project.configuration = 'Release'
    }

    def "push should use remote repository and apiKey"() {
        given:
        project.nuspec {
            packageId = 'Com.Acme.CrossLib'
            packageName = 'CrossLib'
            localRepository = "./customRepo"
            remoteRepository = "http://remote.repository.com"
            apiKey = "123456789"
        }
        project.evaluate()
        task = project.deploy
        task.nuget = nuget

        when:
        task.pushPackage()

        then:
        1 * nuget.push(_, "http://remote.repository.com", "123456789")
    }

    def "install should use local repository"() {
        given:
        project.nuspec {
            packageId = 'Com.Acme.CrossLib'
            packageName = 'CrossLib'
            localRepository = "./customRepo"
            remoteRepository = "http://remote.repository.com"
            apiKey = "123456789"
        }
        project.evaluate()
        task = project.install
        task.nuget = nuget

        when:
        task.pushPackage()

        then:
        1 * nuget.push(_, "./customRepo", null)
    }

    def "install should use default local repository"() {
        given:
        project.nuspec {
            packageId = 'Com.Acme.CrossLib'
            packageName = 'CrossLib'
        }
        project.evaluate()
        task = project.install
        task.nuget = nuget

        when:
        task.pushPackage()

        then:
        1 * nuget.push(_, "${System.getProperty('user.home')}/.nuget", null)
    }

}
