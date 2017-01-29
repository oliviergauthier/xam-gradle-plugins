package com.betomorrow.gradle.application.tasks

import com.betomorrow.ios.plist.InfoPlist
import com.betomorrow.ios.plist.InfoPlistWriter
import com.betomorrow.msbuild.tools.files.FileCopier
import com.betomorrow.xamarin.xbuild.XBuild
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

import java.nio.file.Paths

class BuildIOSAppTaskTest extends Specification {

    FileCopier fileCopier = Mock()
    XBuild xBuild = Mock()
    InfoPlistWriter infoPlistWriter = Mock()

    Project project
    BuildIOSAppTask task

    def setup() {
        project = ProjectBuilder.builder().withProjectDir(new File('src/test/resources/')).build()
        project.apply plugin: 'xamarin-application-plugin'

        project.application {
            solution 'CrossApp/CrossApp.sln'
        }

        project.evaluate()

        task = project.tasks.buildIOS

        task.infoPlistWriter = infoPlistWriter
        task.fileCopier = fileCopier
        task.xBuild = xBuild
    }


    def "should update info.plist"() {
        given:
        InfoPlist capturedInfoPlist
        String capturedInfoPlistOutput

        when:
        task.build()

        then:
        1 * infoPlistWriter.write(_, _) >> { m, out ->
            capturedInfoPlist = m
            capturedInfoPlistOutput = out
        }

        assert capturedInfoPlist.bundleIdentifier == "com.sample.crossapp"
        assert capturedInfoPlist.bundleShortVersion == "2.6"
        assert capturedInfoPlist.bundleVersion == "1.0"
    }

    def "should run xbuild"() {
        given:

        when:
        task.build()

        then:
        1 * xBuild.buildIosApp(
                'Release',
                'iPhone',
                'bin/iPhone/Release',
                project.file("CrossApp/CrossApp.sln").toString())
    }

    def "should copy to output"() {
        given:
        String capturedSrc
        String capturedDst

        when:
        task.build()

        then:
        1 * fileCopier.replace(_,_) >> { src, dst ->
            capturedSrc = src
            capturedDst = dst
        }

        assert Paths.get(capturedSrc) == project.file("CrossApp/iOS/bin/iPhone/Release/CrossApp.iOS.ipa").toPath()
        assert Paths.get(capturedDst) == Paths.get("dist/CrossApp.iOS-1.0.ipa")
    }

    def "should do operations in the right order"() {
        when:
        task.build()

        then:
        1 * infoPlistWriter.write(_, _)

        then:
        1 * xBuild.buildIosApp(_,_,_,_)

        then:
        1 * fileCopier.replace(_,_)
    }

}
