package com.betomorrow.gradle.commons.tasks

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class CleanDistTaskTest extends Specification {

    CleanDistTask task

    def setup() {
        Project project = ProjectBuilder.builder().withProjectDir(new File('src/test/resources/CrossApp/')).build()
        task = project.task("cleanDist", type:CleanDistTask) {}

        createFile('dist/CrossApp.apk')
        createFile('dist/CrossApp.ipa')
        createFile('dist/CrossLin.nupkg')
    }

    def "clean should delete files"() {
        when:
        task.cleanDist()

        then:
        assert !exists('dist')
    }

    private void createFile(String file) {
        Path p = Paths.get('src/test/resources/CrossApp').resolve(file)
        Files.createDirectories(p.parent)
        if (!Files.exists(p)) {
            Files.createFile(p)
            Files.write(p, "Content".getBytes(StandardCharsets.UTF_8))
        }
    }

    private boolean exists(String file) {
        Path p = Paths.get('src/test/resources/CrossApp').resolve(file)
        return Files.exists(p)
    }
}
