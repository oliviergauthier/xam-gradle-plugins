package com.betomorrow.gradle.nugetpackage

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder

/**
 * Created by olivier on 22/02/16.
 */
class NugetPackagePluginTest extends GroovyTestCase {

    void testApply() {
        Project project = ProjectBuilder.builder().build()
        project.pluginManager.apply 'xamarin-nuget-package-plugin'

    }
}
