package com.betomorrow.gradle.commons.tasks

import com.betomorrow.msbuild.tools.descriptors.project.ProjectDescriptor
import com.betomorrow.msbuild.tools.descriptors.solution.SolutionDescriptor
import com.betomorrow.msbuild.tools.descriptors.solution.SolutionLoader
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

import java.nio.file.FileVisitResult
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes

class CleanTask extends DefaultTask {

    protected SolutionLoader solutionLoader = new SolutionLoader();

    protected List<String> buildDirectories = ["obj", "bin"]

    def String solutionFile;
    def String projectFile;

    @TaskAction
    def clean() {
        if (solutionFile != null) {
            SolutionDescriptor sd = solutionLoader.load(solutionFile);
            sd.getProjects().forEach { p ->
                cleanProject(p);
            }
        }

        if (projectFile != null) {
            ProjectDescriptor p = new ProjectDescriptor(null, projectFile);
            cleanProject(p);
        }
    }

    public void cleanProject(ProjectDescriptor p) {
        buildDirectories.forEach { d ->
            Path path = p.path.parent.resolve(d);
            if (Files.exists(path)) {
                deleteDirectory(path);
            }
        }
    }

    public void deleteDirectory(Path directory) {
        Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }

        });
    }

}
