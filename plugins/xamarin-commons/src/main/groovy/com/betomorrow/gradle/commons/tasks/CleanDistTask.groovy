package com.betomorrow.gradle.commons.tasks

import com.betomorrow.xamarin.descriptors.project.ProjectDescriptor
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

import java.nio.file.FileVisitResult
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes

class CleanDistTask extends DefaultTask {

    protected List<String> buildDirectories = ["dist"]

    @TaskAction
    cleanDist() {
        buildDirectories.each {
            deleteDirectory(project.file(it).toPath())
        }
    }

    void deleteDirectory(Path directory) {
        if (Files.exists(directory)) {
            Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
                @Override
                FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file)
                    return FileVisitResult.CONTINUE
                }

                @Override
                FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir)
                    return FileVisitResult.CONTINUE
                }
            })
        }
    }
}
