package com.betomorrow.gradle.application.tasks


import com.betomorrow.msbuild.tools.android.manifest.AndroidManifest
import com.betomorrow.msbuild.tools.android.manifest.AndroidManifestWriter
import com.betomorrow.gradle.application.context.Context
import com.betomorrow.msbuild.tools.files.FileCopier
import com.betomorrow.msbuild.tools.commands.CommandRunner
import com.betomorrow.msbuild.tools.descriptors.project.XamarinProjectDescriptor
import com.betomorrow.msbuild.tools.xbuild.XBuildTargets
import com.betomorrow.msbuild.tools.xbuild.XBuildCmd
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class BuildAndroidAppTask extends DefaultTask {

    protected CommandRunner commandRunner = Context.current.commandRunner
    protected AndroidManifestWriter androidManifestWriter = Context.current.androidManifestWriter
    protected FileCopier fileCopier = Context.current.fileCopier

    String appVersion
    String versionCode
    String packageName
    String output
    String projectFile
    String manifest
    String configuration

    @TaskAction
    def build() {

        updateManifest()

        invokeXBuild()

        copyBuiltAssemblyToOutput()
    }

    private XamarinProjectDescriptor getProjectDescriptor() {
        return new XamarinProjectDescriptor("", projectFile)
    }

    private String getManifestPathFromDescriptor() {
        return getProjectDescriptor().getAndroidManifestPath().toString()
    }

    private void updateManifest() {
        if (manifest != getManifestPathFromDescriptor()) {
            fileCopier.replace(manifest, getManifestPathFromDescriptor())
        }

        def androidManifest = new AndroidManifest()
        androidManifest.versionCode = versionCode
        androidManifest.versionName = appVersion
        androidManifest.packageName = packageName

        androidManifestWriter.write(androidManifest, getManifestPathFromDescriptor())
    }

    private int invokeXBuild() {
        XBuildCmd cmd = new XBuildCmd()
        cmd.setConfiguration(configuration)
        cmd.setTarget(XBuildTargets.PackageForAndroid)
        cmd.setProjectPath(projectFile)
        return commandRunner.run(cmd)
    }

    private void copyBuiltAssemblyToOutput() {
        def outputPath = project.file(output).toPath()
        fileCopier.replace(getProjectDescriptor().getOutputPath(configuration), outputPath)
    }

}
