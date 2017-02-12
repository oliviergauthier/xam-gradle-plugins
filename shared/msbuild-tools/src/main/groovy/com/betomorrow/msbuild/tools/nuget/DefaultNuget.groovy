package com.betomorrow.msbuild.tools.nuget

import com.betomorrow.msbuild.tools.commands.CommandRunner
import com.betomorrow.msbuild.tools.commands.SystemCommandRunner

class DefaultNuget implements Nuget {

    private CommandRunner runner = new SystemCommandRunner()

    DefaultNuget() {
    }

    DefaultNuget(CommandRunner runner) {
        this.runner = runner
    }

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
        runner.run(new NugetCmd(action : 'list',  extra : extra))
    }

    void pack(String packagePath, String suffix) {
        runner.run(new NugetCmd(action: 'pack', ))
    }

    void push(String packagePath, String source, String apiKey) {
        throw new UnsupportedOperationException()
    }

}
