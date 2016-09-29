package com.betomorrow.msbuild.tools.descriptors.solution

import com.betomorrow.msbuild.tools.descriptors.project.ProjectDescriptor

import java.nio.file.Path
import java.nio.file.Paths

class SolutionLoader {

    protected SolutionParser parser = new SolutionParser();

    public SolutionDescriptor load(Path path) {
        Path baseDir = path.parent;
        List<SolutionProject> slnProjects = parser.parse(path);

        Map<String, ProjectDescriptor> descriptors = new HashMap<>();
        slnProjects.each { it -> descriptors.put(it.name, new ProjectDescriptor(it.name, baseDir.resolve(it.path)))}

        return new SolutionDescriptor(descriptors);
    }

    public SolutionDescriptor load(String path) {
        return load(Paths.get(path))
    }


}
