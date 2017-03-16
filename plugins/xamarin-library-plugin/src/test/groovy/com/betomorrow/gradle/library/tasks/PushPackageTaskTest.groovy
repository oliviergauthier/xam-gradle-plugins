package com.betomorrow.gradle.library.tasks

import com.betomorrow.msbuild.tools.nuget.Nuget
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.gradle.wrapper.Install
import spock.lang.Specification

class PushPackageTaskTest extends Specification {

    Nuget nuget

    Project project
    PushPackageTask pushTask

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
            packages {
                SampleLib {
                    packageId = 'Com.Acme.CrossLib'
                }
            }
        }

        project.publish {
            remote {
                url = "http://remote.repository.com"
                apiKey = "123456789"
            }
        }

        project.evaluate()
        pushTask = project.deploySampleLib
        pushTask.nuget = nuget

        when:
        pushTask.pushPackage()

        then:
        1 * nuget.push(_, "http://remote.repository.com", "123456789")
    }


}
