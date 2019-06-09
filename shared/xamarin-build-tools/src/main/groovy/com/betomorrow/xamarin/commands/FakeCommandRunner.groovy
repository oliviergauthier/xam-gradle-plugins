package com.betomorrow.xamarin.commands

class FakeCommandRunner implements CommandRunner {

    List<CommandRunner.Cmd> executedCommands = []

    private boolean verbose

    @Override
    void setVerbose(boolean verbose) {
        this.verbose = verbose
    }

    @Override
    int run(CommandRunner.Cmd cmd) {
        if (verbose) {
            println "Execute command : ${cmd.build().join(" ")}"
        }
        executedCommands.add(cmd)
        return 1
    }
}
