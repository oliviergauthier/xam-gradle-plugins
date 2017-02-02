package com.betomorrow.gradle.library.tasks

import com.betomorrow.xamarin.xbuild.XBuild
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class BuildTaskTest extends Specification {

    XBuild xBuild = Mock()

    Project project
    BuildTask task

    def setup() {
        project = ProjectBuilder.builder().withProjectDir(new File('src/test/resources/')).build()
        project.apply plugin: 'xamarin-library-plugin'

        project.solution = 'CrossLib/CrossLib.sln'

        project.library {
        }

        project.evaluate()

        task = project.tasks.build

        task.xBuild = xBuild
    }

    def "should run xbuild"() {
        given:

        when:
        task.build()

        then:
        1 * xBuild.buildCrossLibrary('Release', "CrossLib/CrossLib.sln")
    }


}
