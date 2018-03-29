package com.betomorrow.gradle.application.tasks

import com.betomorrow.xamarin.ios.plist.InfoPlist
import com.betomorrow.xamarin.ios.plist.InfoPlistWriter
import com.betomorrow.xamarin.files.FileCopier
import com.betomorrow.xamarin.tools.xbuild.XBuild
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

import java.nio.file.Paths

class BuildIOSAppTaskTest extends Specification {

    FileCopier fileCopier = Mock()
    XBuild xBuild = Mock()
    InfoPlistWriter infoPlistWriter = Mock()

    Project project

    def setup() {
        project = ProjectBuilder.builder().withProjectDir(new File('src/test/resources/')).build()
        project.apply plugin: 'xamarin-application-plugin'

        project.solution = 'CrossApp/CrossApp.sln'

    }

    BuildIOSAppTask getIosBuildTask() {
        project.evaluate()

        def task = project.tasks.buildIOS

        task.infoPlistWriter = infoPlistWriter
        task.fileCopier = fileCopier
        task.xBuild = xBuild
        return task
    }


    def "should update info.plist"() {
        given:
        InfoPlist capturedInfoPlist
        String capturedInfoPlistOutput

        when:
        iosBuildTask.build()

        then:
        1 * infoPlistWriter.write(_, _) >> { m, out ->
            capturedInfoPlist = m
            capturedInfoPlistOutput = out
        }

        assert capturedInfoPlist.bundleIdentifier == "com.sample.crossapp"
        assert capturedInfoPlist.bundleShortVersion == "2.6"
        assert capturedInfoPlist.bundleVersion == "1.0"
    }

    def "should run msbuild"() {
        given:
        project.application {
        }

        when:
        iosBuildTask.build()

        then:
        1 * xBuild.buildIosApp(
                'Release',
                'iPhone',
                'bin/iPhone/Release',
                'CrossApp/CrossApp.sln')
    }

    def "should copy to output"() {
        given:
        String capturedSrc
        String capturedDst

        when:
        iosBuildTask.build()

        then:
        1 * fileCopier.copy(_,_) >> { src, dst ->
            capturedSrc = src
            capturedDst = dst
        }

        assert Paths.get(capturedSrc) == project.file("CrossApp/iOS/bin/iPhone/Release/CrossApp.iOS.ipa").toPath()
        assert Paths.get(capturedDst) == Paths.get("dist/CrossApp.iOS-1.0.ipa")
    }

    def "should do operations in the right order"() {
        when:
        iosBuildTask.build()

        then:
        1 * infoPlistWriter.write(_, _)

        then:
        1 * xBuild.buildIosApp(_,_,_,_)

        then:
        1 * fileCopier.copy(_,_)
    }

}
