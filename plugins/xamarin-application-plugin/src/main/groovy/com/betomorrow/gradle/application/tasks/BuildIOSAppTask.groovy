package com.betomorrow.gradle.application.tasks

import com.betomorrow.gradle.application.context.PluginContext
import com.betomorrow.xamarin.ios.plist.InfoPlist
import com.betomorrow.xamarin.ios.plist.InfoPlistWriter
import com.betomorrow.xamarin.files.FileCopier
import com.betomorrow.xamarin.descriptors.project.XamarinProjectDescriptor
import com.betomorrow.xamarin.tools.xbuild.XBuild
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction

class BuildIOSAppTask extends DefaultTask {

    protected XBuild xBuild = PluginContext.current.getXbuild()
    protected FileCopier fileCopier = PluginContext.current.getFileCopier()
    protected InfoPlistWriter infoPlistWriter = PluginContext.current.getInfoPlistWriter()

    String bundleVersion
    String bundleShortVersion
    String bundleIdentifier

    String infoPlist

    String output
    String projectFile
    String solutionFile
    String configuration
    String platform

    @TaskAction
    void build() {

        updatePlistInfo()

        invokeXBuild()

        copyBuiltAssemblyToOutput()
    }

    private void updatePlistInfo() {
        if (infoPlist != getInfoPlistPathFromDescriptor()) {
            fileCopier.copy(infoPlist, getInfoPlistPathFromDescriptor())
        }

        def infPlist = new InfoPlist()
        infPlist.bundleShortVersion = bundleShortVersion
        infPlist.bundleVersion = bundleVersion
        infPlist.bundleIdentifier = bundleIdentifier

        infoPlistWriter.write(infPlist, getInfoPlistPathFromDescriptor())
    }

    private void invokeXBuild() {
        def defaultOutputDir = getProjectDescriptor().getOutputDir(configuration, platform)
        def result = xBuild.buildIosApp(configuration, platform, "bin/$platform/$configuration", solutionFile)
        if (result > 0) {
            throw new GradleException("Can't build ios application")
        }
    }

    private void copyBuiltAssemblyToOutput() {
        def dest = getProjectDescriptor().getApplicationOutputPath(configuration, platform).toString()
        fileCopier.copy(dest, output)
    }

    private XamarinProjectDescriptor getProjectDescriptor() {
        return new XamarinProjectDescriptor("", projectFile)
    }

    private String getInfoPlistPathFromDescriptor() {
        return getProjectDescriptor().getInfoPlistPath().toString()
    }



}
