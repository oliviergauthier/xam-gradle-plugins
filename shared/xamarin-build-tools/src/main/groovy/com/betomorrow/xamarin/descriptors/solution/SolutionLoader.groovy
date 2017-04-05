package com.betomorrow.xamarin.descriptors.solution

import com.betomorrow.xamarin.descriptors.project.XamarinProjectDescriptor

import java.nio.file.Path
import java.nio.file.Paths
import java.util.concurrent.ExecutionException

class SolutionLoader {

    protected SolutionParser parser = new SolutionParser()

    SolutionDescriptor load(Path path) {
        Path baseDir = path.toAbsolutePath().parent
        List<SolutionProject> slnProjects = parser.parse(path)

        Map<String, XamarinProjectDescriptor> descriptors = new HashMap<>()
        slnProjects.each { it ->
            try {
                descriptors.put(it.name, new XamarinProjectDescriptor(it.name, baseDir.resolve(it.path)))
            } catch (Exception e) {

            }
        }

        return new SolutionDescriptor(descriptors)
    }

    SolutionDescriptor load(String path) {
        return load(Paths.get(path))
    }

    SolutionDescriptor load(File file) {
        return load(file.toPath())
    }


}
