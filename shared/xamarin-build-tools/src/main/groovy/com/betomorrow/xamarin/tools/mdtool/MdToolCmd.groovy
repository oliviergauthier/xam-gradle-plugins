package com.betomorrow.xamarin.tools.mdtool

import com.betomorrow.xamarin.commands.CommandRunner

class MdToolCmd implements CommandRunner.Cmd {

    String mdToolPath = 'mdtool'
    String configuration
    String platform
    boolean verbose
    String solutionPath

    List<String> build() {
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

        return cmd
    }
}
