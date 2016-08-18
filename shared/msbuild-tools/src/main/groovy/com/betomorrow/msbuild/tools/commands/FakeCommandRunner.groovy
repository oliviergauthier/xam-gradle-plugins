package com.betomorrow.msbuild.tools.commands

class FakeCommandRunner implements CommandRunner {

    List<CommandRunner.Cmd> executedCommands = []

    @Override
    int run(CommandRunner.Cmd cmd) {
        executedCommands.add(cmd)
        return 1;
    }
}
