package com.betomorrow.xamarin.xbuild

import com.betomorrow.msbuild.tools.commands.CommandRunner
import com.betomorrow.msbuild.tools.commands.SystemCommandRunner

class XBuild {

    public CommandRunner commandRunner = new SystemCommandRunner()

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
}
