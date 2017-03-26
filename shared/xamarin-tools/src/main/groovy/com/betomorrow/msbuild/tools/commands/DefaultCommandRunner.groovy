package com.betomorrow.msbuild.tools.commands

class DefaultCommandRunner implements CommandRunner {

    static DefaultCommandRunner INSTANCE = new DefaultCommandRunner()

    private CommandRunner systemCommandRunner = new SystemCommandRunner()
    private CommandRunner dryRunCommandRunner = new FakeCommandRunner()
    private DefaultCommandRunner() {}

    boolean dryRun

    @Override
    int run(CommandRunner.Cmd cmd) {
        if (dryRun) {
            return dryRunCommandRunner.run(cmd)
        } else {
            return systemCommandRunner.run(cmd)
        }
    }

}
