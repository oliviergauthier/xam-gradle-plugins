package com.betomorrow.gradle.application.tasks

import com.betomorrow.xamarin.android.manifest.AndroidManifest
import com.betomorrow.xamarin.android.manifest.AndroidManifestWriter
import com.betomorrow.gradle.application.context.PluginContext
import com.betomorrow.xamarin.files.FileCopier
import com.betomorrow.xamarin.descriptors.project.XamarinProjectDescriptor
import com.betomorrow.xamarin.tools.xbuild.XBuild
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction

class BuildAndroidAppTask extends DefaultTask {

    protected XBuild xBuild = PluginContext.current.getXbuild()
    protected AndroidManifestWriter androidManifestWriter = PluginContext.current.getAndroidManifestWriter()
    protected FileCopier fileCopier = PluginContext.current.getFileCopier()

    @Input @Optional
    String appVersion

    @Input @Optional
    String versionCode

    @Input @Optional
    String packageName

    @Input @Optional
    String output

    @Input @Optional
    String projectFile

    @Input @Optional
    String manifest

    @Input @Optional
    String configuration

    @TaskAction
    void build() {
        updateManifest()

        invokeMSBuild()

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
            fileCopier.copy(manifest, getManifestPathFromDescriptor())
        }

        def androidManifest = new AndroidManifest()
        androidManifest.versionCode = versionCode
        androidManifest.versionName = appVersion
        androidManifest.packageName = packageName

        androidManifestWriter.write(androidManifest, getManifestPathFromDescriptor())
    }

    private void invokeMSBuild() {
        def result = xBuild.buildAndroidApp(configuration, projectFile)
        if (result > 0) {
            throw new GradleException("Can't build Android application")
        }
    }

    private void copyBuiltAssemblyToOutput() {
        fileCopier.copy(getProjectDescriptor().getApplicationOutputPath(configuration).toString(), output)
    }

}
