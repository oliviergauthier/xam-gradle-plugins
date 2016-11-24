package com.betomorrow.msbuild.tools.descriptors.solution

import com.betomorrow.msbuild.tools.descriptors.project.ProjectDescriptor
import groovy.transform.Canonical

@Canonical
 class SolutionDescriptor {

    private Map<String, ProjectDescriptor> projects

    SolutionDescriptor(Map<String, ProjectDescriptor> projects) {
        this.projects = projects
    }

    ProjectDescriptor getProject(String name) {
        return projects.get(name)
    }

    Collection<ProjectDescriptor> getProjects() {
        return projects.values()
    }

    ProjectDescriptor getFirstAndroidProject() {
        return projects.values().find { it.isAndroidApplication() }
    }

    boolean hasSingleAndroidProject() {
        return projects.values().count { it.isAndroidApplication()} == 1
    }

    ProjectDescriptor getFirstIosProject() {
        return projects.values().find { it.isIosApplication() }
    }

    boolean hasSingleIosProject() {
        return projects.values().count { it.isIosApplication()} == 1
    }
    
    boolean containsProject(String appName) {
        return projects.containsKey(appName)
    }

    boolean containsIosProject() {
        return getFirstIosProject() != null
    }

}
