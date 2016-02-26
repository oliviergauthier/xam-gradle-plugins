package com.betomorrow.gradle.nugetpackage

import com.betomorrow.gradle.nugetpackage.extensions.AssembliesPluginExtension
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

/**
 * Created by olivier on 22/02/16.
 */
class NugetPackagePluginTest {

    @Test
    void testApply() {
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'xamarin-nuget-package-plugin'
        project.nuspec {
            authors "John Doe"
            owners "ACME"
            description "Sample CrossLib build with gradle"

            dependencies {
                dependency "Xamarin.Forms:[1.4.3,)"
                dependency "Xam.ACME.Commons:[1.0.0,)"
            }

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

        AssembliesPluginExtension assembliesPluginExtension =  project.nuspec.extensions.findByName('assemblies')
        println assembliesPluginExtension

    }
}
