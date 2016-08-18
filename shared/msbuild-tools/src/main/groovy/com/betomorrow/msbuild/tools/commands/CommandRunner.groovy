package com.betomorrow.msbuild.tools.commands

interface CommandRunner {

    static CommandRunner INSTANCE = new SystemCommandRunner();

    def boolean dryRun = false;
    int run(Cmd cmd);

    interface Cmd {
        public List<String> build();
    }
}
