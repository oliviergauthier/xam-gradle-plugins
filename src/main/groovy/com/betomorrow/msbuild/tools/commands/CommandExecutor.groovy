package com.betomorrow.msbuild.tools.commands

/**
 * Created by olivier on 17/01/16.
 */
interface CommandExecutor {

    def boolean dryRun = false;

    public int execute(Cmd cmd);

    interface Cmd {
        public Collection<String> build();
    }
}
