package com.betomorrow.gradle.library.tasks

import com.betomorrow.xamarin.assemblyInfo.AssemblyInfoUpdater
import com.betomorrow.xamarin.assemblyInfo.AssemblyInfoUpdaterTask
import com.betomorrow.xamarin.tools.xbuild.XBuild
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class BuildTaskTest extends Specification {

    XBuild xBuild = Mock()
    AssemblyInfoUpdater assemblyInfoUpdater = Mock()
    AssemblyInfoUpdaterTask updater = Mock()

    Project project
    BuildTask task

    def setup() {
        project = ProjectBuilder.builder().withProjectDir(new File('src/test/resources/CrossLib')).build()
        project.apply plugin: 'xamarin-library-plugin'

        project.version = "1.0.0"
    }

    def "should run xbuild"() {
        given:
        project.evaluate()
        task = project.tasks.build
        task.xBuild = xBuild
        task.assemblyInfoUpdater = assemblyInfoUpdater

        def projects = []

        when:
        task.build()

        then:

        4 * assemblyInfoUpdater.from(_) >> { p ->
            projects.add(p)
            return updater
        }
        4 * updater.withVersion("1.0.0") >> { v ->
            return updater
        }

        4 * updater.update()

        1 * xBuild.useMSBuild(false)
        1 * xBuild.buildCrossLibrary('Release', "CrossLib.sln")
    }

    def "should run msbuild"() {
        given:
        project.library {
            useMSBuild = true
        }

        project.evaluate()
        task = project.tasks.build
        task.xBuild = xBuild
        task.assemblyInfoUpdater = assemblyInfoUpdater
        def projects = []

        when:
        task.build()

        then:

        4 * assemblyInfoUpdater.from(_) >> { p ->
            projects.add(p)
            return updater
        }
        4 * updater.withVersion("1.0.0") >> { v ->
            return updater
        }

        4 * updater.update()

        1 * xBuild.useMSBuild(true)
        1 * xBuild.buildCrossLibrary('Release', "CrossLib.sln")
    }


}
