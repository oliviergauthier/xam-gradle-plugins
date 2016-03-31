package com.betomorrow.gradle.nugetpackage

import com.betomorrow.gradle.nugetpackage.extensions.AssembliesPluginExtension
import com.betomorrow.gradle.nugetpackage.extensions.AssemblyTarget
import com.betomorrow.gradle.nugetpackage.extensions.DependenciesPluginExtension
import com.betomorrow.msbuild.tools.nuspec.dependencies.Dependency
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

/**
 * Created by olivier on 22/02/16.
 * https://docs.gradle.org/current/userguide/test_kit.html
 */
class NugetPackagePluginTest {

    @Test
    void testApplyAddAssemblies() {
        Project project = ProjectBuilder.builder().build()
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

    @Test
    void testApplyAddDependencies() {
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'xamarin-nuget-package-plugin'
        project.nuspec {
            dependencies {
                dependency "Xamarin.Forms:[1.4.3,)"
                dependency "Xam.ACME.Commons:[1.0.0,)"
            }
        }

        DependenciesPluginExtension ext =  project.nuspec.extensions.findByName('dependencies')
        assert ext.dependencies.size() == 2
        assert ext.dependencies.get(0) == new Dependency("Xamarin.Forms:[1.4.3,)")
        assert ext.dependencies.get(1) == new Dependency("Xam.ACME.Commons:[1.0.0,)")
    }
}
