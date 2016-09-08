package com.betomorrow.msbuild.tools.commands

class FakeCommandRunner implements CommandRunner {

    List<CommandRunner.Cmd> executedCommands = []

    @Override
    int run(CommandRunner.Cmd cmd) {
        println "Execute command : ${cmd.build().join(" ")}";
        executedCommands.add(cmd)
        return 1;
    }
}
