package com.betomorrow.gradle.library

import com.betomorrow.gradle.commons.tasks.CleanTask
import com.betomorrow.gradle.library.extensions.nuspec.AssembliesPluginExtension
import com.betomorrow.gradle.library.extensions.nuspec.AssemblyTarget
import com.betomorrow.gradle.library.extensions.nuspec.DependenciesPluginExtension
import com.betomorrow.gradle.library.tasks.BuildTask
import com.betomorrow.msbuild.tools.nuspec.dependencies.Dependency
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class XamarinLibraryPluginTest extends Specification {

    Project project

    def "setup"() {
        project = ProjectBuilder.builder().withProjectDir(new File('src/test/resources/CrossLib')).build()
        project.apply plugin: 'xamarin-library-plugin'

        project.configuration = 'Debug'

        project.evaluate()
    }

    def "should create build task"() {
        when:
        def task = project.tasks.build

        then:
        assert task instanceof BuildTask
        assert task.solutionFile == 'CrossLib.sln'
        assert task.configuration == 'Debug'
    }

    def "should create build task with release configuration by default"() {
        given:
        project = ProjectBuilder.builder().withProjectDir(new File('src/test/resources/CrossLib')).build()
        project.apply plugin: 'xamarin-library-plugin'

        project.evaluate()

        when:
        def task = project.tasks.build

        then:
        assert task instanceof BuildTask
        assert task.solutionFile == 'CrossLib.sln'
        assert task.configuration == 'Release'


    }

    def "should create clean task"() {
        when:
        def task = project.tasks.clean

        then:
        assert task instanceof CleanTask
        assert task.solutionFile == 'CrossLib.sln'
    }

    def "test apply add assemblies"() {
        when:
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'xamarin-library-plugin'
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

        then:
        AssembliesPluginExtension ext =  project.nuspec.extensions.findByName('assemblies')
        assert ext.assemblies.size() == 3

        AssemblyTarget target1 = ext.assemblies.get(0)
        assert target1.getDest() == "lib/portable-net45+wp8+wpa81+win8+MonoAndroid10+MonoTouch10+Xamarin.iOS10"
        assert target1.getIncludes() == ["Xam.ACME.CrossLib.dll"]

        AssemblyTarget target2 = ext.assemblies.get(1)
        assert target2.getDest() == "lib/MonoAndroid10"
        assert target2.getIncludes() == [ "Xam.ACME.CrossLib.dll", "Xam.ACME.CrossLib.Droid.dll", "Xam.ACME.CrossLib.Binding.Droid.dll"]

        AssemblyTarget target3 = ext.assemblies.get(2)
        assert target3.getDest() == "lib/Xamarin.iOS10"
        assert target3.getIncludes() == [ "Xam.ACME.CrossLib.dll", "Xam.ACME.CrossLib.IOS.dll", "Xam.ACME.CrossLib.Binding.IOS.dll"]
    }


    def "test apply add dependencies"() {
        when:
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'xamarin-library-plugin'
        project.nuspec {
            dependencies {
                dependency "Xamarin.Forms:[1.4.3,)"
                dependency "Xam.ACME.Commons:[1.0.0,)"
            }
        }

        then:
        DependenciesPluginExtension ext =  project.nuspec.extensions.findByName('dependencies')
        assert ext.dependencies.size() == 2
        assert ext.dependencies.get(0) == new Dependency("Xamarin.Forms:[1.4.3,)")
        assert ext.dependencies.get(1) == new Dependency("Xam.ACME.Commons:[1.0.0,)")
    }


}
