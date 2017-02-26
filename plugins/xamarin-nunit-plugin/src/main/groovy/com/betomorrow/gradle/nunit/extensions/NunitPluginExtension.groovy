package com.betomorrow.gradle.nunit.extensions

import org.gradle.api.Project

class NunitPluginExtension {

    def projects
    def assemblies
    String format

    NunitPluginExtension() {
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


    List<String> getAssemblies() {
        if (assemblies != null) {
            if (assemblies in Collection) {
                return assemblies
            } else {
                return [assemblies]
            }
        }
    }

}
