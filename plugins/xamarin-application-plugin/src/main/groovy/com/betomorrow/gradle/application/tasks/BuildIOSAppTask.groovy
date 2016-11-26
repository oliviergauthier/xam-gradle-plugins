package com.betomorrow.gradle.application.tasks

import com.betomorrow.ios.plist.InfoPlistWriter
import com.betomorrow.gradle.application.context.Context
import com.betomorrow.ios.plist.InfoPlist
import com.betomorrow.msbuild.tools.commands.CommandRunner
import com.betomorrow.msbuild.tools.files.FileCopier
import com.betomorrow.msbuild.tools.mdtool.MdToolCmd
import com.betomorrow.xamarin.descriptors.project.ProjectDescriptor
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class BuildIOSAppTask extends DefaultTask {

    protected CommandRunner commandRunner = Context.current.commandRunner
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
    def build() {

        updatePlistInfo()

        invokeMDTool()

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

    private int invokeMDTool() {
        MdToolCmd cmd = new MdToolCmd()
        cmd.setConfiguration(configuration)
        cmd.setSolutionPath(solutionFile)
        cmd.setPlatform(platform)
        return commandRunner.run(cmd)
    }

    private void copyBuiltAssemblyToOutput() {
        fileCopier.replace(getProjectDescriptor().getOutputPath(configuration, platform).toString(), output)
    }

    private ProjectDescriptor getProjectDescriptor() {
        return new ProjectDescriptor("", projectFile)
    }

    private String getInfoPlistPathFromDescriptor() {
        return getProjectDescriptor().getInfoPlistPath().toString()
    }



}
