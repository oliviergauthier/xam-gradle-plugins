package com.betomorrow.gradle.application.extensions

import com.betomorrow.gradle.base.extensions.XamarinBaseExtension
import org.gradle.api.Project

class XamarinAndroidApplicationExtension {

    def String output;
    def String manifest;
    def String projectFile;

    private XamarinBaseExtension baseExtension;

    public XamarinAndroidApplicationExtension(Project project) {
        baseExtension = project.extensions.getByName("xamarin")
    }

    public String getProjectFile() {
        if (projectFile != null) {
            return projectFile;
        }

        return baseExtension.solution;
    }


}
