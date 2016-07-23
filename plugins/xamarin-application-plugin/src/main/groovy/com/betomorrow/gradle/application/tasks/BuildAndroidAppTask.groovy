package com.betomorrow.gradle.application.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Created by olivier on 05/07/16.
 */
class BuildAndroidAppTask extends DefaultTask {

    def String appVersion;
    def String storeVersion;
    def String packageName;
    def String output;
    def String projectFile;
    def String manifest;

    @TaskAction
    def build() {
        println("App Version : ${appVersion}")
        println("Store Version : ${storeVersion}")
        println("Package Name : ${packageName}")
        println("Manifest : ${manifest}")
        println("output : ${output}")
        println("projectFile : ${projectFile}")
    }

}
