package com.betomorrow.gradle.nunit.extensions

import org.gradle.api.Project

class NunitPluginExtension {

    def projects
    String format

    NunitPluginExtension(Project project) {
    }

    List<String> getProjects() {
        if (projects != null) {
            if (projects in Collection) {
                return projects
            } else {
                return [projects]
            }
        }
    }

}
