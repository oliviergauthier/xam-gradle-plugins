package com.betomorrow.msbuild.tools.solution;

import com.betomorrow.msbuild.tools.csproj.ProjectDescriptor
import groovy.transform.Canonical;

import java.util.Collection;
import java.util.Map;

/**
 * Created by olivier on 13/06/16.
 */
@Canonical
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

    public ProjectDescriptor getAndroidProject() {
        return projects.values().find { it.isAndroidApplication() }
    }

    public boolean hasSingleAndroidProject() {
        return projects.values().count { it.isAndroidApplication()} == 1;
    }

    public boolean containsApp(String appName) {
        return projects.containsKey(appName)
    }

}
