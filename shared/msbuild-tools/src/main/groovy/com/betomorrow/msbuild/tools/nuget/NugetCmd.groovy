package com.betomorrow.msbuild.tools.nuget

import com.betomorrow.msbuild.tools.commands.CommandRunner

class NugetCmd implements CommandRunner.Cmd{

    String nugetPath = 'nugetpackage'
    String action
    String packageId
    String packageVersion

    String[] extra

    @Override
     List<String> build() {
        List<String> cmd = [nugetPath, action]

        if (packageId) {
            cmd.add(packageId)
        }

        if (packageVersion) {
            cmd.add(packageVersion)
        }

        if (extra) {
            cmd.addAll(extra)
        }

        return cmd
    }


}
