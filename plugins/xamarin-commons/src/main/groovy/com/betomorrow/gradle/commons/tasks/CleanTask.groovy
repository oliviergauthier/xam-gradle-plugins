package com.betomorrow.gradle.commons.tasks

import com.betomorrow.xamarin.descriptors.project.ProjectDescriptor
import com.betomorrow.xamarin.descriptors.solution.SolutionDescriptor
import com.betomorrow.xamarin.descriptors.solution.SolutionLoader
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction

import java.nio.file.FileVisitResult
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes

class CleanTask extends DefaultTask {

    protected SolutionLoader solutionLoader = new SolutionLoader()

    protected List<String> buildDirectories = ["obj", "bin"]

    @Input @Optional
    String solutionFile

    @Input @Optional
    String projectFile

    @TaskAction
    clean() {
        if (solutionFile != null) {
            SolutionDescriptor sd = solutionLoader.load(project.file(solutionFile))
            sd.getProjects().forEach { p ->
                cleanProject(p)
            }
        }

        if (projectFile != null) {
            ProjectDescriptor p = new ProjectDescriptor(null, projectFile)
            cleanProject(p)
        }
    }

    void cleanProject(ProjectDescriptor p) {
        buildDirectories.forEach { d ->
            deleteDirectory(p.path.parent.resolve(d))
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
