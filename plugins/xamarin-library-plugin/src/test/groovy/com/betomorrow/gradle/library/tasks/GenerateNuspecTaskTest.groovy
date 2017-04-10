package com.betomorrow.gradle.library.tasks

import com.betomorrow.xamarin.tools.nuspec.NuSpec
import com.betomorrow.xamarin.tools.nuspec.NuSpecWriter
import com.betomorrow.xamarin.tools.nuspec.assemblies.Assembly
import com.betomorrow.xamarin.tools.nuspec.dependencies.Dependency
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

import java.nio.file.Paths

class GenerateNuspecTaskTest extends Specification {

    Project project
    GenerateNuspecTask task

    NuSpecWriter writer = Mock()

    def "setup"() {
        project = ProjectBuilder.builder().withProjectDir(new File('src/test/resources/CrossLib')).build()
        project.apply plugin: 'xamarin-library-plugin'

        project.solution = 'CrossLib.sln'
        project.configuration = 'Release'
    }

    def "should contains info"() {
        given:
        NuSpec nuSpecData
        project.nuspec {
            packages {
                SampleLib {
                    title = "aTitle"
                    packageId = "aPackageId"
                    version = "aVersion"
                    authors = "some authors"
                    owners = "some owners"
                    licenseUrl = "the license url"
                    projectUrl = "the project url"
                    iconUrl = "the icon url"
                    requireLicenseAcceptance = true
                    description = "a description"
                    releaseNotes = "the release notes"
                    copyright = "the copyright"
                    tags = "some tags"
                }
            }
        }

        when:
        project.evaluate();
        task = project.tasks.generateNuspecSampleLib
        task.writer = writer
        task.generateNuspec()

        then:
        1 * writer.write(_) >> { arguments -> nuSpecData = arguments[0] }
        assert nuSpecData.title == "aTitle"
        assert nuSpecData.owners == "some owners"
        assert nuSpecData.packageId == "aPackageId"
        assert nuSpecData.version == "aVersion"
        assert nuSpecData.authors == "some authors"
        assert nuSpecData.licenseUrl == "the license url"
        assert nuSpecData.projectUrl == "the project url"
        assert nuSpecData.iconUrl == "the icon url"
        assert nuSpecData.requireLicenseAcceptance == true
        assert nuSpecData.description == "a description"
        assert nuSpecData.releaseNotes == "the release notes"
        assert nuSpecData.copyright == "the copyright"
        assert nuSpecData.tags == "some tags"
    }

    def "should inherit info from global config"() {
        given:
        NuSpec nuSpecData
        project.nuspec {

            title = "aTitle"
            version = "aVersion"
            authors = "some authors"
            owners = "some owners"
            licenseUrl = "the license url"
            projectUrl = "the project url"
            iconUrl = "the icon url"
            requireLicenseAcceptance = true
            description = "a description"
            releaseNotes = "the release notes"
            copyright = "the copyright"
            tags = "some tags"

            packages {
                SampleLib {
                    packageId = "aPackageId"
                }
            }
        }

        when:
        project.evaluate();
        task = project.tasks.generateNuspecSampleLib
        task.writer = writer
        task.generateNuspec()

        then:
        1 * writer.write(_) >> { arguments -> nuSpecData = arguments[0] }
        assert nuSpecData.owners == "some owners"
        assert nuSpecData.packageId == "aPackageId"
        assert nuSpecData.version == "aVersion"
        assert nuSpecData.title == "aTitle"
        assert nuSpecData.authors == "some authors"
        assert nuSpecData.licenseUrl == "the license url"
        assert nuSpecData.projectUrl == "the project url"
        assert nuSpecData.iconUrl == "the icon url"
        assert nuSpecData.requireLicenseAcceptance == true
        assert nuSpecData.description == "a description"
        assert nuSpecData.releaseNotes == "the release notes"
        assert nuSpecData.copyright == "the copyright"
        assert nuSpecData.tags == "some tags"
    }

    def "should contains dependencies"() {
        given:
        NuSpec nuSpecData
        project.nuspec {
            packages {
                SampleLib {
                    dependencies {
                        dependency "Xamarin.Forms:[1.4.3,)"
                        dependency "net40:Xam.ACME.Commons:[1.0.0,)"
                    }
                }
            }
        }

        when:
        project.evaluate();
        task = project.tasks.generateNuspecSampleLib
        task.writer = writer
        task.generateNuspec()

        then:
        1 * writer.write(_) >> { arguments -> nuSpecData = arguments[0] }

        assert nuSpecData.dependencySet.contains(new Dependency("Xamarin.Forms:[1.4.3,)"))
        assert nuSpecData.dependencySet.contains(new Dependency("net40:Xam.ACME.Commons:[1.0.0,)"))
    }

    def "should contains assemblies"() {
        given:
        NuSpec nuSpecData
        project.nuspec {
            packages {
                SampleLib {
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
        }

        when:
        project.evaluate();
        task = project.tasks.generateNuspecSampleLib
        task.writer = writer
        task.generateNuspec()

        then:
        1 * writer.write(_) >> { arguments -> nuSpecData = arguments[0] }

        assert nuSpecData.assemblySet.contains(new Assembly('Xam.ACME.CrossLib.dll',
                'lib/portable-net45+wp8+wpa81+win8+MonoAndroid10+MonoTouch10+Xamarin.iOS10'))

        assert nuSpecData.assemblySet.contains(new Assembly('Xam.ACME.CrossLib.dll',
                'lib/MonoAndroid10'))

        assert nuSpecData.assemblySet.contains(new Assembly('Xam.ACME.CrossLib.Droid.dll',
                'lib/MonoAndroid10'))

        assert nuSpecData.assemblySet.contains(new Assembly('Xam.ACME.CrossLib.Binding.Droid.dll',
                'lib/MonoAndroid10'))

        assert nuSpecData.assemblySet.contains(new Assembly('Xam.ACME.CrossLib.dll',
                'lib/Xamarin.iOS10'))

        assert nuSpecData.assemblySet.contains(new Assembly('Xam.ACME.CrossLib.IOS.dll',
                'lib/Xamarin.iOS10'))

        assert nuSpecData.assemblySet.contains(new Assembly('Xam.ACME.CrossLib.Binding.IOS.dll',
                'lib/Xamarin.iOS10'))


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
            packages {
                SampleLib {
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
            }
        }

        when:
        project.evaluate()
        task = project.tasks.generateNuspecSampleLib
        task.writer = writer
        task.generateNuspec()

        then:
        1 * writer.write(_) >> { arguments -> nuSpecData = arguments[0] }

        assert nuSpecData.assemblySet.contains(new Assembly(Paths.get('CrossLib/bin/Release/CrossLib.dll').toString(),
                'lib/portable-net45+wp8+wpa81+win8+MonoAndroid10+MonoTouch10+Xamarin.iOS10'))

        assert nuSpecData.assemblySet.contains(new Assembly(Paths.get('CrossLib.Abstractions/bin/Release/CrossLib.Abstractions.dll').toString(),
                'lib/MonoAndroid10'))

        assert nuSpecData.assemblySet.contains(new Assembly(Paths.get('CrossLib.Droid/bin/Release/CrossLib.Droid.dll').toString(),
                'lib/MonoAndroid10'))

        assert nuSpecData.assemblySet.contains(new Assembly(Paths.get('CrossLib.Abstractions/bin/Release/CrossLib.Abstractions.dll').toString(),
                'lib/Xamarin.iOS10'))

        assert nuSpecData.assemblySet.contains(new Assembly(Paths.get('CrossLib.IOS/bin/Release/CrossLib.IOS.dll').toString(),
                'lib/Xamarin.iOS10'))

    }

}
