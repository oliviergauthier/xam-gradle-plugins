package com.betomorrow.gradle.library.tasks

import com.betomorrow.xamarin.assemblyInfo.AssemblyInfoUpdater
import com.betomorrow.xamarin.xbuild.XBuild
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class BuildTaskTest extends Specification {

    XBuild xBuild = Mock()
    AssemblyInfoUpdater assemblyInfoUpdater = Mock()
    AssemblyInfoUpdater.AssemblyInfoUpdaterTask updater = Mock()

    Project project
    BuildTask task

    def setup() {
        project = ProjectBuilder.builder().withProjectDir(new File('src/test/resources/CrossLib')).build()
        project.apply plugin: 'xamarin-library-plugin'

        project.evaluate()

        task = project.tasks.build
        task.xBuild = xBuild
        task.assemblyInfoUpdater = assemblyInfoUpdater
    }

    def "should run xbuild"() {
        given:

        when:
        task.build()

        then:
        5 * assemblyInfoUpdater.from(_) << { p ->
            return updater
        }
        5 * updater.withVersion(_) << { v ->
            return updater
        }

        1 * xBuild.buildCrossLibrary('Release', "CrossLib/CrossLib.sln")
    }


}
