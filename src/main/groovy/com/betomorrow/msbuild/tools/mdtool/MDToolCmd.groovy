package com.betomorrow.msbuild.tools.mdtool

import com.betomorrow.msbuild.tools.commands.CommandExecutor

/**
 * Created by Olivier on 15/01/2016.
 */
class MDToolCmd implements CommandExecutor.Cmd {

    String mdToolPath = 'mdtool'
    String configuration;
    String platform;
    boolean verbose;
    String solutionPath;

    public Collection<String> build() {
        def cmd = [mdToolPath]

        if (verbose) {
            cmd.add('-v')
        }

        cmd.add('build')

        if (configuration) {
            def confAndPlatform = configuration

            if (platform) {
                confAndPlatform += "|${platform}"
            }

            cmd.add("--configuration:${confAndPlatform}")
        }

        cmd.add(solutionPath)

        return cmd;
    }
}
