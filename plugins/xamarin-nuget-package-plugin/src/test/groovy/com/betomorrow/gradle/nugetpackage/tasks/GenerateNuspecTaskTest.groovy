package com.betomorrow.gradle.nugetpackage.tasks

import com.betomorrow.msbuild.tools.nuspec.NuSpec
import com.betomorrow.msbuild.tools.nuspec.NuSpecWriter
import com.betomorrow.msbuild.tools.nuspec.assemblies.Assembly
import com.betomorrow.msbuild.tools.nuspec.dependencies.Dependency
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class GenerateNuspecTaskTest extends Specification {

    Project project
    GenerateNuspecTask task

    NuSpecWriter writer = Mock()

    def "setup"() {
        project = ProjectBuilder.builder().build()
        project.apply plugin: 'xamarin-nuget-package-plugin'

        project.solution = 'CrossLib/CrossLib.sln'
        project.configuration = 'Release'
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

    def "should contains assemblies"() {
        given:
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

        when:
        project.evaluate();
        task = project.tasks.generateNuspec

        then:
        HashMap<String, List<String>> assemblies = [:]
        task.assemblies.forEach{
            assemblies[it.dest] = it.includes
        }

        assert assemblies['lib/portable-net45+wp8+wpa81+win8+MonoAndroid10+MonoTouch10+Xamarin.iOS10']
                .containsAll(["Xam.ACME.CrossLib.dll"])

        assert assemblies['lib/MonoAndroid10']
                .containsAll( [ "Xam.ACME.CrossLib.dll",
                                "Xam.ACME.CrossLib.Droid.dll",
                                "Xam.ACME.CrossLib.Binding.Droid.dll"])

        assert assemblies['lib/Xamarin.iOS10']
                .containsAll( [ "Xam.ACME.CrossLib.dll",
                                "Xam.ACME.CrossLib.IOS.dll",
                                "Xam.ACME.CrossLib.Binding.IOS.dll"])

    }

    def "should resolve assemblies by project names"() {
        given:
        NuSpec nuSpecData
        project.nuspec {
            assemblies {
                target {
                    dest "lib/portable-net45+wp8+wpa81+win8+MonoAndroid10+MonoTouch10+Xamarin.iOS10"
                    includes "CrossLib"
                }

                target {
                    dest "lib/MonoAndroid10"
                    includes "CrossLib.Abstractions",
                             "CrossLib.Droid"
                }

                target {
                    dest "lib/Xamarin.iOS10"
                    includes "CrossLib.Abstractions",
                             "CrossLib.IOS"
                }
            }
        }

        when:
        project.evaluate()
        task = project.tasks.generateNuspec
        task.writer = writer
        task.generateNuspec()

        then:
        1 * writer.write(_) >> { arguments -> nuSpecData = arguments[0] }

        assert nuSpecData.assemblySet.contains(new Assembly('CrossLib/bin/Release/bin/Release/CrossLib.dll',
                'lib/portable-net45+wp8+wpa81+win8+MonoAndroid10+MonoTouch10+Xamarin.iOS10'))

        assert nuSpecData.assemblySet.contains(new Assembly('CrossLib.Abstractions/bin/Release/CrossLib.Abstractions.dll',
                'lib/MonoAndroid10'))

        assert nuSpecData.assemblySet.contains(new Assembly('CrossLib.Drois/bin/Release/CrossLib.Droid.dll',
                'lib/MonoAndroid10'))

        assert nuSpecData.assemblySet.contains(new Assembly('CrossLib.Abstractions/bin/Release/CrossLib.Abstractions.dll',
                'lib/Xamarin.iOS10'))

        assert nuSpecData.assemblySet.contains(new Assembly('CrossLib.Drois/bin/Release/CrossLib.IOS.dll',
                'lib/Xamarin.iOS10'))

    }

}
