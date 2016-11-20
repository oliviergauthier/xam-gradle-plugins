package com.betomorrow.gradle.application.tasks


import com.betomorrow.android.tools.manifest.AndroidManifest
import com.betomorrow.android.tools.manifest.AndroidManifestWriter
import com.betomorrow.gradle.application.context.Context
import com.betomorrow.msbuild.tools.Files.FileCopier
import com.betomorrow.msbuild.tools.commands.CommandRunner
import com.betomorrow.msbuild.tools.descriptors.project.ProjectDescriptor
import com.betomorrow.msbuild.tools.xbuild.XBuildTargets
import com.betomorrow.msbuild.tools.xbuild.XBuildCmd
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class BuildAndroidAppTask extends DefaultTask {

    protected CommandRunner commandRunner = Context.current.commandRunner;
    protected AndroidManifestWriter androidManifestWriter = Context.current.androidManifestWriter;
    protected FileCopier fileCopier = Context.current.fileCopier;

    def String appVersion;
    def String versionCode;
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
        if (manifest != getManifestPathFromDescriptor()) {
            fileCopier.replace(manifest, getManifestPathFromDescriptor());
        }

        def androidManifest = new AndroidManifest();
        androidManifest.versionCode = versionCode
        androidManifest.versionName = appVersion
        androidManifest.packageName = packageName

        androidManifestWriter.write(androidManifest, getManifestPathFromDescriptor());
    }

    private int invokeXBuild() {
        XBuildCmd cmd = new XBuildCmd()
        cmd.setConfiguration(configuration)
        cmd.setTarget(XBuildTargets.PackageForAndroid)
        cmd.setProjectPath(projectFile)
        return commandRunner.run(cmd)
    }

    private void copyBuiltAssemblyToOutput() {
        // TODO find a better place for outputPath resolution
        def xamarinOutputPath = getProjectDescriptor().getOutputDir(configuration).resolve(packageName + ".apk");
        def outputPath = project.file(output).toPath();
        fileCopier.replace(xamarinOutputPath, outputPath);
    }

}
