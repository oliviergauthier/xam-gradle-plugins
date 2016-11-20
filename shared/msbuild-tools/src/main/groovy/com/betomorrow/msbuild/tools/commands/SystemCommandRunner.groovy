package com.betomorrow.msbuild.tools.commands

class SystemCommandRunner implements CommandRunner {

    def workingDirectory =  new File(System.properties['user.dir'].toString());

    @Override
    int run(CommandRunner.Cmd cmd) {
        println "Execute command : ${cmd.build().join(" ")}";

        def process = new ProcessBuilder(cmd.build() as String[])
                .directory(workingDirectory)
                .inheritIO()
                .start();

        process.waitFor();

        return process.exitValue();
    }
}
