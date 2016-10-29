package com.betomorrow.gradle.commons.tasks

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class CleanTaskTest extends Specification {

    CleanTask task;

    def setup() {
        Project project = ProjectBuilder.builder().build()
        task = project.task("clean", type:CleanTask) {
            solutionFile = 'src/test/resources/CrossApp/CrossApp.sln'
        }

        createFile('CrossApp/bin/Release/CrossApp.dll')
        createFile('Droid/bin/Release/CrossApp.Droid.dll')
        createFile('iOS/bin/iPhone/Release/CrossApp.iOS.dll')

        createFile('CrossApp/obj/Release/CrossApp.dll')
        createFile('Droid/obj/Release/CrossApp.Droid.dll')
        createFile('iOS/obj/iPhone/Release/CrossApp.iOS.dll')
    }

    def "clean should delete files"() {
        when:
        task.clean()

        then:
        assert !exists('CrossApp/bin/')
        assert !exists('Droid/bin/')
        assert !exists('iOS/bin/')
        assert !exists('CrossApp/obj/')
        assert !exists('Droid/obj/')
        assert !exists('iOS/obj/')
    }

    private void createFile(String file) {
        Path p = Paths.get('src/test/resources/CrossApp').resolve(file);
        Files.createDirectories(p.parent);
        if (!Files.exists(p)) {
            Files.createFile(p);
            Files.write(p, "Content".getBytes(StandardCharsets.UTF_8));
        }
    }

    private boolean exists(String file) {
        Path p = Paths.get('src/test/resources/CrossApp').resolve(file);
        return Files.exists(p);
    }
}
