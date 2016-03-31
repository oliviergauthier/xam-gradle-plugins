package com.betomorrow.msbuild.tools.commands

/**
 * Created by olivier on 17/01/16.
 */
interface CommandRunner {

    static CommandRunner INSTANCE = new SystemCommandRunner();

    def boolean dryRun = false;
    int run(Cmd cmd);

    interface Cmd {
        public Collection<String> build();
    }
}
