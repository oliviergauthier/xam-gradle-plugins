package com.betomorrow.msbuild.tools.xbuild

import com.betomorrow.msbuild.tools.commands.CommandRunner
import com.betomorrow.msbuild.tools.commands.SystemCommandRunner

class XBuild {

    public CommandRunner commandRunner = new SystemCommandRunner();

    public void build(String configuration, String file) {
        XBuildCmd cmd = new XBuildCmd()
        cmd.setConfiguration(configuration)
        cmd.setTarget(XBuildTargets.Build)
        cmd.setProjectPath(file)
        commandRunner.run(cmd);
    }

    public void clean() {
        throw new UnsupportedOperationException()
    }

    public void signAndroidPackage() {
        throw new UnsupportedOperationException()
    }
}
