package com.betomorrow.msbuild.tools.solution

import com.betomorrow.msbuild.tools.csproj.ProjectDescriptor

/**
 * Created by olivier on 13/06/16.
 */
class SolutionLoader {

    protected SolutionParser parser = new SolutionParser();

    public SolutionDescriptor load(String path) {
        List<SolutionProject> slnProjects = parser.parse(path);

        Map<String, ProjectDescriptor> descriptors = new HashMap<>();
        slnProjects.each { it -> descriptors.put(it.name, it.path)}

        return new SolutionDescriptor(descriptors);
    }

}
