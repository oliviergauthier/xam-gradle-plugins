package com.betomorrow.gradle.application.tasks

import com.betomorrow.gradle.application.context.Context
import com.betomorrow.ios.tools.plist.InfoPlist
import com.betomorrow.ios.tools.plist.InfoPlistWriter
import com.betomorrow.msbuild.tools.Files.FileCopier
import com.betomorrow.msbuild.tools.commands.CommandRunner
import com.betomorrow.msbuild.tools.descriptors.project.ProjectDescriptor
import com.betomorrow.msbuild.tools.mdtool.MdToolCmd
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Created by olivier on 26/07/16.
 */
class BuildIOSAppTask extends DefaultTask {

    protected CommandRunner commandRunner = Context.current.commandRunner;
    protected FileCopier fileCopier = Context.current.fileCopier;
    protected InfoPlistWriter infoPlistWriter = Context.current.infoPlistWriter;

    def String bundleVersion;
    def String bundleShortVersion;
    def String bundleIdentifier;

    def String infoPlist;

    def String output;
    def String projectFile;
    def String solutionFile;
    def String configuration;
    def String platform;

    @TaskAction
    def build() {

        updatePlistInfo();

        invokeMDTool();

        copyBuiltAssemblyToOutput();
    }

    private void updatePlistInfo() {
        if (infoPlist != getInfoPlistPathFromDescriptor()) {
            fileCopier.replace(infoPlist, getInfoPlistPathFromDescriptor());
        }

        def infPlist = new InfoPlist();
        infPlist.bundleShortVersion = bundleShortVersion
        infPlist.bundleVersion = bundleVersion
        infPlist.bundleIdentifier = bundleIdentifier

        infoPlistWriter.write(infPlist, getInfoPlistPathFromDescriptor());
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
        return new ProjectDescriptor("", projectFile);
    }

    private String getInfoPlistPathFromDescriptor() {
        return getProjectDescriptor().getInfoPlistPath().toString()
    }



}
