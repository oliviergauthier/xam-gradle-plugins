package com.betomorrow.xamarin.descriptors.solution

import com.betomorrow.xamarin.descriptors.project.XamarinProjectDescriptor
import groovy.transform.Canonical

@Canonical
class SolutionDescriptor {

    private Map<String, XamarinProjectDescriptor> projects

    SolutionDescriptor(Map<String, XamarinProjectDescriptor> projects) {
        this.projects = projects
    }

    XamarinProjectDescriptor getProject(String name) {
        return projects.get(name)
    }

    Collection<XamarinProjectDescriptor> getProjects() {
        return projects.values()
    }

    XamarinProjectDescriptor getFirstAndroidProject() {
        return projects.values().find { it.isAndroidApplication() }
    }

    boolean hasSingleAndroidProject() {
        return projects.values().count { it.isAndroidApplication() } == 1
    }

    XamarinProjectDescriptor getFirstIosProject() {
        return projects.values().find { it.isIosApplication() }
    }

    boolean hasSingleIosProject() {
        return projects.values().count { it.isIosApplication() } == 1
    }

    boolean containsProject(String appName) {
        return projects.containsKey(appName)
    }

    boolean containsIosProject() {
        return getFirstIosProject() != null
    }

}
