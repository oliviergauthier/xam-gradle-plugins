package com.betomorrow.gradle.base

import com.betomorrow.gradle.base.extensions.XamarinBaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by olivier on 19/04/16.
 */
class XamarinBasePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.extensions.create("xamarin", XamarinBaseExtension);

//        project.afterEvaluate {
//            def extension = project.extensions.findByType(XamarinBaseExtension)
//            println extension.solution
//        }
    }
}
