package com.betomorrow.gradle.nugetpackage

import com.betomorrow.gradle.nugetpackage.extensions.NuspecPluginExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by olivier on 22/02/16.
 */
class NugetPackagePlugin implements Plugin<Project>{

    /**
     * http://stackoverflow.com/questions/28999106/define-nested-extension-containers-in-gradle
     */
    @Override
    void apply(Project project) {
        project.extensions.create("nuspec", NuspecPluginExtension)
    }

}
