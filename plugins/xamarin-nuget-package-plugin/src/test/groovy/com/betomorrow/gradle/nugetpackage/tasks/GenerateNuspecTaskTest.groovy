package com.betomorrow.gradle.nugetpackage.tasks

import com.betomorrow.msbuild.tools.nuspec.dependencies.Dependency
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class GenerateNuspecTaskTest extends Specification {


    Project project
    GenerateNuspecTask task

    def "setup"() {
        project = ProjectBuilder.builder().build()
        project.apply plugin: 'xamarin-nuget-package-plugin'
        project.nuspec {
            assemblies {
                target {
                    dest "lib/portable-net45+wp8+wpa81+win8+MonoAndroid10+MonoTouch10+Xamarin.iOS10"
                    includes "Xam.ACME.CrossLib.dll"
                }

                target {
                    dest "lib/MonoAndroid10"
                    includes "Xam.ACME.CrossLib.dll",
                            "Xam.ACME.CrossLib.Droid.dll",
                            "Xam.ACME.CrossLib.Binding.Droid.dll"
                }

                target {
                    dest "lib/Xamarin.iOS10"
                    includes "Xam.ACME.CrossLib.dll",
                            "Xam.ACME.CrossLib.IOS.dll",
                            "Xam.ACME.CrossLib.Binding.IOS.dll"
                }
            }



        }

    }

    def "should contains info"() {
        given:
        project.nuspec {
            packageId "aPackageId"
            version "aVersion"
            authors "some authors"
            owners "some owners"
            licenseUrl "the license url"
            projectUrl "the project url"
            iconUrl "the icon url"
            requireLicenseAcceptance true
            description "a description"
            releaseNotes "the release notes"
            copyright "the copyright"
            tags "some tags"
        }

        when:
        project.evaluate();
        task = project.tasks.generateNuspec

        then:
        assert task.owners == "some owners"
        assert task.packageId == "aPackageId"
        assert task.version == "aVersion"
        assert task.authors == "some authors"
        assert task.licenseUrl == "the license url"
        assert task.projectUrl == "the project url"
        assert task.iconUrl == "the icon url"
        assert task.requireLicenseAcceptance == true
        assert task.description == "a description"
        assert task.releaseNotes == "the release notes"
        assert task.copyright == "the copyright"
        assert task.tags == "some tags"
    }

    def "should contains dependencies"() {
        given:
        project.nuspec {
            dependencies {
                dependency "Xamarin.Forms:[1.4.3,)"
                dependency "net40:Xam.ACME.Commons:[1.0.0,)"
            }
        }

        when:
        project.evaluate();
        task = project.tasks.generateNuspec

        then:
        assert task.dependencies.contains(new Dependency("Xamarin.Forms:[1.4.3,)"))
        assert task.dependencies.contains(new Dependency("net40:Xam.ACME.Commons:[1.0.0,)"))
    }

}
