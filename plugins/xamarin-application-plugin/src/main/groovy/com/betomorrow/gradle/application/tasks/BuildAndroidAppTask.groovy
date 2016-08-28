package com.betomorrow.gradle.application.tasks

import com.betomorrow.android.tools.AndroidManifestEditor
import com.betomorrow.msbuild.tools.FileUtils
import com.betomorrow.msbuild.tools.commands.CommandRunner
import com.betomorrow.msbuild.tools.commands.DefaultCommandRunner
import com.betomorrow.msbuild.tools.descriptors.project.ProjectDescriptor
import com.betomorrow.msbuild.tools.xbuild.AndroidTargets
import com.betomorrow.msbuild.tools.xbuild.XBuildCmd
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption


class BuildAndroidAppTask extends DefaultTask {

    protected CommandRunner commandRunner = DefaultCommandRunner.INSTANCE;

    def String appVersion;
    def String storeVersion;
    def String packageName;
    def String output;
    def String projectFile;
    def String manifest;
    def String configuration;

    @TaskAction
    def build() {

        String manifestPathFromDescriptor = getManifestPathFromDescriptor()

        if (manifestPathFromDescriptor != manifest) {
            FileUtils.replace(manifest, manifestPathFromDescriptor)
        }

        updateManifest(manifestPathFromDescriptor);

        invokeXBuild()

        copyBuiltAssemblyToOutput();

    }

    private ProjectDescriptor getProjectDescriptor() {
        return new ProjectDescriptor("", projectFile);
    }

    private String getManifestPathFromDescriptor() {
        def manifestRelativePath = getProjectDescriptor().androidManifest.replace("\\", "/")
        return Paths.get(projectFile).parent.resolve(manifestRelativePath).toString()
    }

    private void updateManifest(String path) {
        def editor = new AndroidManifestEditor(path);
        editor.versionCode = appVersion
        editor.versionName = storeVersion
        editor.packageName = packageName
        editor.write();
    }

    private int invokeXBuild() {
        XBuildCmd cmd = new XBuildCmd()
        cmd.setConfiguration(configuration)
        cmd.setTarget(AndroidTargets.Build)
        cmd.setProjectPath(projectFile)
        return commandRunner.run(cmd)
    }

    private void copyBuiltAssemblyToOutput() {
        FileUtils.replace(getProjectDescriptor().getOutputPath(configuration).toString(), output)
    }

}
