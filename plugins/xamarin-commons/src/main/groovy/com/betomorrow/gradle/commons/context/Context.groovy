package com.betomorrow.gradle.commons.context

import org.gradle.api.Project

class Context<T> {

    private static T instance

    protected static T defaultContext
    protected static T dryRunContext

    static T getCurrent() {
        return instance
    }

    static void configure(Project project) {
        configure(project.hasProperty("dryRun") && project.dryRun)
    }

    static void configure(boolean dryRun) {
        if (dryRun) {
            instance = dryRunContext
        } else {
            instance = defaultContext
        }
    }

}
