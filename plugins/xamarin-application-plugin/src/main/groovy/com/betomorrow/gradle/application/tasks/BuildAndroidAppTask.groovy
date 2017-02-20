package com.betomorrow.gradle.application.tasks

import com.betomorrow.android.manifest.AndroidManifest
import com.betomorrow.android.manifest.AndroidManifestWriter
import com.betomorrow.gradle.application.context.PluginContext
import com.betomorrow.msbuild.tools.files.FileCopier
import com.betomorrow.xamarin.descriptors.project.XamarinProjectDescriptor
import com.betomorrow.xamarin.xbuild.XBuild
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class BuildAndroidAppTask extends DefaultTask {

    protected XBuild xBuild = PluginContext.current.xbuild
    protected AndroidManifestWriter androidManifestWriter = PluginContext.current.androidManifestWriter
    protected FileCopier fileCopier = PluginContext.current.fileCopier

    String appVersion
    String versionCode
    String packageName
    String output
    String projectFile
    String manifest
    String configuration

    @TaskAction
    void build() {
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

    private void invokeXBuild() {
        xBuild.buildAndroidApp(configuration, projectFile)
    }

    private void copyBuiltAssemblyToOutput() {
        fileCopier.replace(getProjectDescriptor().getApplicationOutputPath(configuration).toString(), output)
    }

}
