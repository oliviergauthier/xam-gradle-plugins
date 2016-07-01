package com.betomorrow.gradle.application

import com.betomorrow.gradle.application.extensions.XamarinAndroidApplicationExtension
import com.betomorrow.gradle.application.extensions.XamarinApplicationExtension
import com.betomorrow.gradle.application.extensions.XamarinIosApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class XamarinApplicationPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
//        project.beforeEvaluate {
//            buildscript.configurations.classpath += 'com.betomorrow.gradle:xamarin-base-plugin:1.0-SNAPSHOT'
//        }
//
//        project.configure(project) {
//            apply plugin: 'xamarin-base-plugin'
//        }

        project.extensions.create("application", XamarinApplicationExtension)
        project.application.extensions.create("android", XamarinAndroidApplicationExtension)
        project.application.extensions.create("ios", XamarinIosApplicationExtension)

        project.application.extensions.getByType(XamarinAndroidApplicationExtension).manifest = "toto";


    }

}
