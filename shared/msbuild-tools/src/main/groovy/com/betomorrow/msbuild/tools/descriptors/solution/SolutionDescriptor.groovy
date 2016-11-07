package com.betomorrow.msbuild.tools.descriptors.solution;

import com.betomorrow.msbuild.tools.descriptors.project.ProjectDescriptor
import groovy.transform.Canonical

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

    public ProjectDescriptor getFirstAndroidProject() {
        return projects.values().find { it.isAndroidApplication() }
    }

    public boolean hasSingleAndroidProject() {
        return projects.values().count { it.isAndroidApplication()} == 1;
    }

    public ProjectDescriptor getFirstIosProject() {
        return projects.values().find { it.isIosApplication() }
    }

    public boolean hasSingleIosProject() {
        return projects.values().count { it.isIosApplication()} == 1;
    }
    
    public boolean containsProject(String appName) {
        return projects.containsKey(appName)
    }

    public boolean containsIosProject() {
        return getFirstIosProject() != null;
    }

}
