package com.betomorrow.gradle.library.tasks

import com.betomorrow.msbuild.tools.nuget.Nuget
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class InstallPackageTaskTest extends Specification {

    Nuget nuget

    Project project
    InstallPackageTask installTask

    def "setup"() {
        nuget = Mock()

        project = ProjectBuilder.builder()
                .withProjectDir(new File('src/test/resources/CrossLib')).build()
        project.apply plugin: 'xamarin-library-plugin'

        project.solution = 'CrossLib.sln'
        project.configuration = 'Release'
    }


    def "install should use local repository"() {
        given:
        project.nuspec {
            packageId = 'Com.Acme.CrossLib'
        }
        project.publish {
            local {
                path = "./customRepo"
            }
        }
        project.evaluate()
        installTask = project.install
        installTask.nuget = nuget

        when:
        installTask.installPackage()

        then:
        1 * nuget.install(_, "./customRepo")

    }

    def "install should use default local repository"() {
        given:
        project.nuspec {
            packageId = 'Com.Acme.CrossLib'
        }
        project.evaluate()
        installTask = project.install
        installTask.nuget = nuget

        when:
        installTask.installPackage()

        then:
        1 * nuget.install(_, "${System.getProperty('user.home')}${File.separator}.nuget${File.separator}packages")
    }

    def "install should use given format"(){
        given:
        project.nuspec {
            packageId = 'Com.Acme.CrossLib'
        }

        project.publish {
            local {
                path = "./customRepo"
                format = "nuget2"
            }
        }

        project.evaluate()
        installTask = project.install
        installTask.nuget = nuget

        when:
        installTask.installPackage()

        then:
        1 * nuget.push(_, "./customRepo", null)
    }

    def "install should throw exception on unknown format"() {
        given:
        project.nuspec {
            packageId = 'Com.Acme.CrossLib'
        }

        project.publish {
            local {
                format = "nuget1"
            }
        }

        project.evaluate()
        installTask = project.install
        installTask.nuget = nuget

        when:
        installTask.installPackage()

        then:
            thrown GradleException
    }
}
