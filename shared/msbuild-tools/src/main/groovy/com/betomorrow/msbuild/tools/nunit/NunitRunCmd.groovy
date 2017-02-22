package com.betomorrow.msbuild.tools.nunit

import com.betomorrow.msbuild.tools.commands.CommandRunner

class NunitRunCmd implements CommandRunner.Cmd {

    String nunitConsolePath = 'nunit-console.exe'

    List<String> assemblies

    String format
    String resultFilename = 'TestResult.xml'

    NunitRunCmd(List<String> assemblies, String format) {
        this.assemblies = assemblies
        this.format = format
    }

    @Override
    List<String> build() {
        List<String> cmd = [nunitConsolePath]

        if (assemblies) {
            cmd.addAll(assemblies)
        }

        if (format) {
            cmd.add("--result:${resultFilename};format=${format}")
        }

        return cmd
    }
}
