package com.betomorrow.gradle.commons.tasks

import org.gradle.api.Project

class GlobalVariables {

    private static String DEFAULT_CONFIGURATION = 'Release'

    static void initVariables(Project project) {

        project.ext.set("configuration", DEFAULT_CONFIGURATION)

        def files = new FileNameFinder().getFileNames(project.projectDir.absolutePath, '*.sln')
        if (files != null && files.size() > 0) {
            project.ext.set("solution", new File(files[0]).name)
        } else {
            project.ext.set("solution", "")
        }

    }

}
