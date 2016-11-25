package com.betomorrow.msbuild.tools.descriptors.solution

import com.betomorrow.msbuild.tools.descriptors.project.XamarinProjectDescriptor

import java.nio.file.Path
import java.nio.file.Paths

class SolutionLoader {

    protected SolutionParser parser = new SolutionParser()

    SolutionDescriptor load(Path path) {
        Path baseDir = path.toAbsolutePath().parent
        List<SolutionProject> slnProjects = parser.parse(path)

        Map<String, XamarinProjectDescriptor> descriptors = new HashMap<>()
        slnProjects.each { it ->
            descriptors.put(it.name, new XamarinProjectDescriptor(it.name, baseDir.resolve(it.path)))
        }

        return new SolutionDescriptor(descriptors)
    }

    SolutionDescriptor load(String path) {
        return load(Paths.get(path))
    }


}
