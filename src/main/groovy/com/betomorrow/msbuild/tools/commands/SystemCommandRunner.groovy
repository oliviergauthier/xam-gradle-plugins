package com.betomorrow.msbuild.tools.commands

/**
 * Created by olivier on 17/01/16.
 */
class SystemCommandRunner implements CommandRunner {

    def workingDirectory =  new File(System.properties['user.dir']);

    @Override
    int run(CommandRunner.Cmd cmd) {
        def process = new ProcessBuilder(cmd.build()).directory(workingDirectory).redirectErrorStream().strart();
        process.waitFor();
        return process.exitValue();
    }
}
