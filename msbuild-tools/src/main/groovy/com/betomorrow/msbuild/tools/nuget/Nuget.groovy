package com.betomorrow.msbuild.tools.nuget

import com.betomorrow.msbuild.tools.commands.CommandRunner
import com.betomorrow.msbuild.tools.commands.SystemCommandRunner

/**
 * Created by olivier on 17/01/16.
 */
class Nuget {

    protected CommandRunner runner = CommandRunner.INSTANCE;

    public void install(String packageId, String version) {
        runner.run(new NugetCmd(action : 'install', packageId : packageId, version : version))
    }

    public void restore() {
        runner.run(new NugetCmd(action : 'restore'))
    }

    public void update(String packageId, String version) {
        runner.run(new NugetCmd(action : 'install', packageId : packageId, version : version))
    }

    public void delete(String packageId, String version) {
        runner.run(new NugetCmd(action : 'delete', packageId : packageId, version : version))
    }

    public void list(boolean preRelease = false, boolean allVersions = false) {
        def extra = []
        if (allVersions) {
            extra.add('-allversions')
        }
        if (preRelease) {
            extra.add('-prerelease')
        }
        runner.run(new NugetCmd(action : 'liste',  extra : extra))
    }

    public void pack() {
        throw new UnsupportedOperationException()
    }

    public void deploy() {
        throw new UnsupportedOperationException()
    }


}
