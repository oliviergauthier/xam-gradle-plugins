package com.betomorrow.msbuild.tools.commands

/**
 * Created by olivier on 14/02/16.
 */
class FakeCommandRunner implements CommandRunner {

    List<CommandRunner.Cmd> executedCommands = []

    @Override
    int run(CommandRunner.Cmd cmd) {
        return executedCommands.add(cmd)
    }
}
