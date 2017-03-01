package com.betomorrow.xamarin.xbuild

import com.betomorrow.msbuild.tools.commands.CommandRunner
import com.betomorrow.msbuild.tools.commands.SystemCommandRunner

class XBuild {

    private CommandRunner commandRunner = new SystemCommandRunner()

    XBuild() {
    }

    XBuild(CommandRunner runner) {
        commandRunner = runner
    }

    void buildAndroidApp(String configuration, String projectFile) {
        XBuildCmd cmd = new XBuildCmd()
        cmd.setConfiguration(configuration)
        cmd.setTarget(XBuildTargets.PackageForAndroid)
        cmd.setProjectPath(projectFile)
        commandRunner.run(cmd)
    }

    void buildIosApp(String configuration, String platform, String outputDir, String solutionPath) {
        XBuildCmd cmd = new XBuildCmd()
        cmd.setConfiguration(configuration)
        cmd.addProperty('Platform', platform)
        if (outputDir != null && !outputDir.isEmpty()) {
            cmd.addProperty('IpaPackageDir', outputDir)
        }
        cmd.setTarget(XBuildTargets.Build)
        cmd.setProjectPath(solutionPath)
        commandRunner.run(cmd)
    }

    void buildCrossLibrary(String configuration, String solutionPath) {
        XBuildCmd cmd = new XBuildCmd()
        cmd.setConfiguration(configuration)
        cmd.setTarget(XBuildTargets.Build)
        cmd.setProjectPath(solutionPath)
        commandRunner.run(cmd)
    }

    void buildSingleProject(String configuration, String csprojPath) {
        XBuildCmd cmd = new XBuildCmd()
        cmd.setConfiguration(configuration)
        cmd.setTarget(XBuildTargets.Build)
        cmd.setProjectPath(csprojPath)
        commandRunner.run(cmd)
    }
}
