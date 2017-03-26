package com.betomorrow.gradle.nunit.tasks

import com.betomorrow.xamarin.tools.xbuild.XBuild
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class CompileTestTaskTest extends Specification {

    Project project

    CompileTestTask task
    XBuild xbuild

    def "setup"(){
        xbuild = Mock()

        project = ProjectBuilder.builder().withProjectDir(new File('src/test/resources/CrossLib')).build()
        project.apply plugin: 'xamarin-nunit-plugin'

        project.solution = 'CrossLib.sln'
        project.configuration = 'Release'
    }

    def "run should resolve projects assemblies"() {
        given:
        project.nunit  {
            projects = 'CrossLib.Test'
        }

        when:
        project.evaluate()
        task = project.compileTest
        task.xbuild = xbuild
        task.run()

        then:
        1 * xbuild.buildSingleProject('Release', project.file('CrossLib.Test/CrossLib.Test.csproj').toString())
    }

}
