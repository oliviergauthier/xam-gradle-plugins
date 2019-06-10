package com.betomorrow.xamarin.commands

class DefaultCommandRunner implements CommandRunner {

    static DefaultCommandRunner INSTANCE = new DefaultCommandRunner()

    private CommandRunner systemCommandRunner = new SystemCommandRunner()
    private CommandRunner dryRunCommandRunner = new FakeCommandRunner()
    private DefaultCommandRunner() {}

    boolean dryRun

    @Override
    void setVerbose(boolean verbose) {
        systemCommandRunner.setVerbose(verbose)
        dryRunCommandRunner.setVerbose(verbose)
    }

    @Override
    int run(CommandRunner.Cmd cmd) {
        if (dryRun) {
            return dryRunCommandRunner.run(cmd)
        } else {
            return systemCommandRunner.run(cmd)
        }
    }

}
