package com.betomorrow.msbuild.tools.nuget

import com.betomorrow.msbuild.tools.commands.CommandRunner

/**
 * Created by olivier on 17/01/16.
 */
class NugetCmd implements CommandRunner.Cmd{

    String nugetPath = 'nuget'
    String action
    String packageId
    String packageVersion

    String[] extra;

    @Override
    public Collection<String> build() {
        def cmd = [nugetPath, action]

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
