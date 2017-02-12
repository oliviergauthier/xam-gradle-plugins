package com.betomorrow.gradle.application.tasks

import com.betomorrow.gradle.application.context.Context
import com.betomorrow.ios.plist.InfoPlist
import com.betomorrow.ios.plist.InfoPlistWriter
import com.betomorrow.msbuild.tools.files.FileCopier
import com.betomorrow.xamarin.descriptors.project.XamarinProjectDescriptor
import com.betomorrow.xamarin.xbuild.XBuild
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class BuildIOSAppTask extends DefaultTask {

    protected XBuild xBuild = Context.current.xbuild
    protected FileCopier fileCopier = Context.current.fileCopier
    protected InfoPlistWriter infoPlistWriter = Context.current.infoPlistWriter

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
            fileCopier.replace(infoPlist, getInfoPlistPathFromDescriptor())
        }

        def infPlist = new InfoPlist()
        infPlist.bundleShortVersion = bundleShortVersion
        infPlist.bundleVersion = bundleVersion
        infPlist.bundleIdentifier = bundleIdentifier

        infoPlistWriter.write(infPlist, getInfoPlistPathFromDescriptor())
    }

    private void invokeXBuild() {
        def defaultOutputDir = getProjectDescriptor().getOutputDir(configuration, platform)
        xBuild.buildIosApp(configuration, platform, "bin/$platform/$configuration", solutionFile)
    }

    private void copyBuiltAssemblyToOutput() {
        fileCopier.replace(getProjectDescriptor().getApplicationOutputPath(configuration, platform).toString(), output)
    }

    private XamarinProjectDescriptor getProjectDescriptor() {
        return new XamarinProjectDescriptor("", projectFile)
    }

    private String getInfoPlistPathFromDescriptor() {
        return getProjectDescriptor().getInfoPlistPath().toString()
    }



}
