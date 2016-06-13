package com.betomorrow.msbuild.tools.solution;

import com.betomorrow.msbuild.tools.csproj.ProjectDescriptor;

import java.util.Collection;
import java.util.Map;

/**
 * Created by olivier on 13/06/16.
 */
public class SolutionDescriptor {

    private Map<String, ProjectDescriptor> projects;

    public SolutionDescriptor(Map<String, ProjectDescriptor> projects) {
        this.projects = projects
    }

    public ProjectDescriptor getProject(String name) {
        return projects.get(name);
    }

    public Collection<ProjectDescriptor> getProjects() {
        return projects.values();
    }

}
