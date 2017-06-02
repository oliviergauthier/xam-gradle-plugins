package com.betomorrow.gradle.application.tasks

import com.betomorrow.xamarin.android.manifest.AndroidManifest
import com.betomorrow.xamarin.android.manifest.AndroidManifestWriter
import com.betomorrow.xamarin.files.FileCopier
import com.betomorrow.xamarin.tools.xbuild.XBuild
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

import java.nio.file.Paths

class BuildAndroidAppTaskTest extends Specification {

    AndroidManifestWriter manifestWriter = Mock()
    FileCopier fileCopier = Mock()
    XBuild xbuild = Mock()

    Project project

    def setup() {
        project = ProjectBuilder.builder().withProjectDir(new File('src/test/resources/')).build()
        project.apply plugin: 'xamarin-application-plugin'

        project.solution = 'CrossApp/CrossApp.sln'

    }

    BuildAndroidAppTask getAndroidTask() {
        project.evaluate()

        def task = project.tasks.buildAndroid

        task.androidManifestWriter = manifestWriter
        task.fileCopier = fileCopier
        task.xBuild = xbuild
        return task
    }


    def "should update manifest"() {
        given:
        AndroidManifest capturedManifest
        String capturedManifestOutput

        when:
        androidTask.build()

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

        when:
        androidTask.build()

        then:
        1 * xbuild.buildAndroidApp("Release",  project.file("CrossApp/Droid/CrossApp.Droid.csproj").toString())
    }

    def "should run msbuild"() {
        given:
        project.application {
            useMSBuild = true
        }

        when:
        androidTask.build()

        then:
        1 * xbuild.useMSBuild(true)

        then:
        1 * xbuild.buildAndroidApp("Release",  project.file("CrossApp/Droid/CrossApp.Droid.csproj").toString())
    }


    def "should copy to output"() {
        given:
        String capturedSrc
        String capturedDst

        when:
        androidTask.build()

        then:
        1 * fileCopier.copy(_,_) >> { src, dst ->
            capturedSrc = src
            capturedDst = dst
        }

        assert Paths.get(capturedSrc) == project.file("CrossApp/Droid/bin/Release/com.acme.crossapp.apk").toPath()
        assert Paths.get(capturedDst) == Paths.get("dist/CrossApp.Droid-1.0.apk")
    }

    def "should do operations in the right order"() {
        when:
        androidTask.build()

        then:
        1 * manifestWriter.write(_, _)

        then:
        1 * xbuild.buildAndroidApp(_,_)

        then:
        1 * fileCopier.copy(_,_)
    }

}
