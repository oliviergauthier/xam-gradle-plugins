package com.betomorrow.gradle.commons.extensions

import org.gradle.api.GradleException
import org.gradle.api.Project

class AbstractXamarinExtension {

    protected Project project

    AbstractXamarinExtension(Project project) {
        this.project = project
    }

    String getConfiguration() {
        return project.configuration
    }

    String getSolution() {
        if (!project.solution) {
            throw new GradleException("No solution file defined")
        }
        return project.solution
    }


}
