package com.betomorrow.msbuild.tools.commands

class SystemCommandRunner implements CommandRunner {

    def workingDirectory =  new File(System.properties['user.dir'].toString());

    @Override
    int run(CommandRunner.Cmd cmd) {
        def process = new ProcessBuilder(cmd.build()).directory(workingDirectory).redirectErrorStream().strart();
        process.waitFor();
        return process.exitValue();
    }
}
