package com.betomorrow.gradle.application.tasks

import com.betomorrow.android.manifest.AndroidManifest
import com.betomorrow.android.manifest.AndroidManifestWriter
import com.betomorrow.msbuild.tools.files.FileCopier
import com.betomorrow.msbuild.tools.commands.CommandRunner
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

import java.nio.file.Paths

class BuildAndroidAppTaskTest extends Specification {

    AndroidManifestWriter manifestWriter = Mock()
    FileCopier fileCopier = Mock()
    CommandRunner runner = Mock()

    BuildAndroidAppTask task
    Project project

    def setup() {
        project = ProjectBuilder.builder().withProjectDir(new File('src/test/resources/')).build()
        project.apply plugin: 'xamarin-application-plugin'

        project.application {
            solution 'CrossApp/CrossApp.sln'
        }

        project.evaluate()

        task = project.tasks.buildAndroid

        task.androidManifestWriter = manifestWriter
        task.fileCopier = fileCopier
        task.commandRunner = runner
    }


    def "should update manifest"() {
        given:
        AndroidManifest capturedManifest
        String capturedManifestOutput

        when:
        task.build()

        then:
        1 * manifestWriter.write(_, _) >> { m, out ->
            capturedManifest = m
            capturedManifestOutput = out
        }

        assert capturedManifest.packageName == "com.acme.crossapp"
        assert capturedManifest.versionCode == "2.6"
        assert capturedManifest.versionName == "1.0"
    }


    def "should run xbuild"() {
        given:
        CommandRunner.Cmd capturedCmd

        when:
        task.build()

        then:
        1 * runner.run(_) >> { cmd ->
            capturedCmd = cmd[0]
            return 1
        }

        def expectedPath = project.file("CrossApp/Droid/CrossApp.Droid.csproj").toString()
        assert capturedCmd.build() == ["xbuild", "/t:PackageForAndroid", "/p:Configuration=Release", expectedPath]
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

        assert Paths.get(capturedSrc) == project.file("CrossApp/Droid/bin/Release/com.acme.crossapp.apk").toPath()
        assert Paths.get(capturedDst) == project.file("dist/CrossApp.Droid-1.0.apk").toPath()
    }

    def "should do operations in the right order"() {
        when:
        task.build()

        then:
        1 * manifestWriter.write(_, _)

        then:
        1 * runner.run(_) >> 1

        then:
        1 * fileCopier.replace(_,_)
    }

}
