package com.betomorrow.gradle.application.tasks

import com.betomorrow.android.tools.AndroidManifestEditorFactory
import com.betomorrow.msbuild.tools.Files.FakeFileCopier
import com.betomorrow.msbuild.tools.Files.FileCopier
import com.betomorrow.msbuild.tools.commands.CommandRunner
import com.betomorrow.msbuild.tools.commands.DefaultCommandRunner
import com.betomorrow.msbuild.tools.descriptors.project.ProjectDescriptor
import com.betomorrow.msbuild.tools.xbuild.AndroidTargets
import com.betomorrow.msbuild.tools.xbuild.XBuildCmd
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class BuildAndroidAppTask extends DefaultTask {

    protected CommandRunner commandRunner = DefaultCommandRunner.INSTANCE;
    protected AndroidManifestEditorFactory androidManifestEditorFactory = new AndroidManifestEditorFactory();
    protected FileCopier fileCopier = new FakeFileCopier();

    def String appVersion;
    def String storeVersion;
    def String packageName;
    def String output;
    def String projectFile;
    def String manifest;
    def String configuration;

    @TaskAction
    def build() {

        updateManifest();

        invokeXBuild()

        copyBuiltAssemblyToOutput();
    }

    private ProjectDescriptor getProjectDescriptor() {
        return new ProjectDescriptor("", projectFile);
    }

    private String getManifestPathFromDescriptor() {
        return getProjectDescriptor().getAndroidManifestPath().toString()
    }

    private void updateManifest() {
        def editor = androidManifestEditorFactory.create(manifest);
        editor.versionCode = appVersion
        editor.versionName = storeVersion
        editor.packageName = packageName
        editor.write(getManifestPathFromDescriptor());
    }

    private int invokeXBuild() {
        XBuildCmd cmd = new XBuildCmd()
        cmd.setConfiguration(configuration)
        cmd.setTarget(AndroidTargets.Build)
        cmd.setProjectPath(projectFile)
        return commandRunner.run(cmd)
    }

    private void copyBuiltAssemblyToOutput() {
        fileCopier.replace(getProjectDescriptor().getOutputPath(configuration).toString(), output)
    }

}
