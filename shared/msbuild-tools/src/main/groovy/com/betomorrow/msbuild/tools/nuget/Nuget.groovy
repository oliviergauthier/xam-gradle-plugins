package com.betomorrow.msbuild.tools.nuget

import com.betomorrow.msbuild.tools.commands.CommandRunner
import com.betomorrow.msbuild.tools.commands.SystemCommandRunner

class Nuget {

    protected CommandRunner runner = CommandRunner.INSTANCE

    void install(String packageId, String version) {
        runner.run(new NugetCmd(action : 'install', packageId : packageId, version : version))
    }

    void restore() {
        runner.run(new NugetCmd(action : 'restore'))
    }

    void update(String packageId, String version) {
        runner.run(new NugetCmd(action : 'install', packageId : packageId, version : version))
    }

    void delete(String packageId, String version) {
        runner.run(new NugetCmd(action : 'delete', packageId : packageId, version : version))
    }

    void list(boolean preRelease = false, boolean allVersions = false) {
        def extra = []
        if (allVersions) {
            extra.add('-allversions')
        }
        if (preRelease) {
            extra.add('-prerelease')
        }
        runner.run(new NugetCmd(action : 'liste',  extra : extra))
    }

    void pack() {
        throw new UnsupportedOperationException()
    }

    void deploy() {
        throw new UnsupportedOperationException()
    }


}
