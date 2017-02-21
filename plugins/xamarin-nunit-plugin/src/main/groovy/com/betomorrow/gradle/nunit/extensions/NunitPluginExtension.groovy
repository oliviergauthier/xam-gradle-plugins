package com.betomorrow.gradle.nunit.extensions

import org.gradle.api.Project

class NunitPluginExtension {

    List<String> projects
    String format

    NunitPluginExtension(Project project) {
    }

    List<String> getProjects() {
        if (projects != null) {
            return projects
        }
    }

}
