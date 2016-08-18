package com.betomorrow.gradle.application.tasks

import com.betomorrow.android.tools.AndroidManifestEditor
import com.betomorrow.msbuild.tools.descriptors.project.ProjectDescriptor
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption


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

        if (manifestPathFromDescriptor != manifest) {
            copy(manifest, manifestPathFromDescriptor)
        }

        updateManifest(manifestPathFromDescriptor);

//        xbuild()

//        copyBuiltAssemblyToOutput();

    }

    private ProjectDescriptor getProjectDescriptor() {
        return new ProjectDescriptor("", projectFile);
    }

    private String getManifestPathFromDescriptor() {
        def manifestRelativePath = projectDescriptor.androidManifest.replace("\\", "/")
        return Paths.get(projectFile).parent.resolve(manifestRelativePath).toString()
    }

    private void copy(String src, String dst) {
        Files.copy(Paths.get(src), Paths.get(dst), StandardCopyOption.REPLACE_EXISTING)
    }

    private void updateManifest(String path) {
        def editor = new AndroidManifestEditor(path);
        editor.versionCode = appVersion
        editor.versionName = storeVersion
        editor.packageName = packageName
        editor.write();
    }

}
